package com.example.mini_project.ui.screens.GraphScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mini_project.data.AppContainer
import com.example.mini_project.data.category.CategoriesRepository
import com.example.mini_project.data.category.Category
import com.example.mini_project.data.task.Task
import com.example.mini_project.data.task.TasksRepository
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.model.ExtraStore
import com.patrykandpatrick.vico.core.model.columnSeries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class GraphViewModel(
    private val tasksRepository: TasksRepository,
    private val categoriesRepository: CategoriesRepository
    ): ViewModel() {

    val graphWindowUIState = MutableStateFlow(GraphWindowUIState())

    val graphUiState : StateFlow<GraphUiState> = combine(
        tasksRepository.getTasks(),
        categoriesRepository.listOfAllCategoriesSortedByCurrentLevel()
    ) { tasks, categories -> GraphUiState(tasks, categories) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = GraphUiState()
    )

    val chartProducer = CartesianChartModelProducer.build()

    fun tryCategoryGraph(modelProducer: CartesianChartModelProducer = chartProducer, data: Pair<List<String>, List<Float>> = pairCategoryList(graphUiState.value.categoryList), listKey : ExtraStore.Key<List<String>>) : CartesianChartModelProducer {

        return updateModelProducer(modelProducer = modelProducer, data = data, listKey = listKey)

    }

    fun tryTaskGraph(modelProducer: CartesianChartModelProducer = chartProducer, data: Pair<List<String>, List<Float>> = pairTaskList(graphUiState.value.taskList), listKey : ExtraStore.Key<List<String>>) : CartesianChartModelProducer {

        return updateModelProducer(modelProducer = modelProducer, data = data, listKey = listKey)

    }

    private fun updateModelProducer(modelProducer: CartesianChartModelProducer = chartProducer, data : Pair<List<String>, List<Float>>, listKey : ExtraStore.Key<List<String>>) : CartesianChartModelProducer {
        if (data.second.isNotEmpty() and data.first.isNotEmpty()) {
            modelProducer.tryRunTransaction {
                columnSeries {series(data.second)}
                updateExtras { it[listKey] = data.first }
            }
        }
        return modelProducer
    }
    fun switchGraphViewed() {

        graphWindowUIState.update {x -> x.copy(showingCategories = !x.showingCategories)}

    }

    fun pairTaskList(taskList: List<Task> = graphUiState.value.taskList) : Pair<List<String>, List<Float>> {

        val tempX = mutableListOf<String>()
        val tempY = mutableListOf<Float>()

        for ((index, task) in taskList.withIndex()) {

            tempX.add(index, task.title)
            tempY.add(index, task.streak.toFloat())

        }
        return Pair(tempX, tempY)
    }

    fun pairCategoryList (categoryList: List<Category>) : Pair<List<String>, List<Float>> {
        val tempX = mutableListOf<String>()
        val tempY = mutableListOf<Float>()

        for ((index, category) in categoryList.withIndex()) {

            tempX.add(index, category.name.toString())
            tempY.add(index, category.currentLevel.toFloat())
        }

        return Pair(tempX, tempY)
    }
}

data class GraphUiState(
    val taskList: List<Task> = listOf(),
    val categoryList: List<Category> = listOf(),
)

data class GraphWindowUIState(
    val showingCategories : Boolean = true
)