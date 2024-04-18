package com.example.mini_project.ui.screens.home

import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberStandardBottomSheetState
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mini_project.OurApplication
import com.example.mini_project.data.task.TaskDao
import androidx.lifecycle.viewModelScope
import androidx.room.DatabaseConfiguration
import com.example.mini_project.data.AppContainer
import com.example.mini_project.data.task.Frequency
import com.example.mini_project.data.category.Category
import com.example.mini_project.data.task.Categories
import com.example.mini_project.data.task.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



/*
* Holds and exposes the state the UI consumes.
* ViewModel stores the app-related data that isn't destroyed when the activity is destroyed and recreated by Android framework.
* Unlike the activity instance, ViewModel objects are not destroyed.
* The app automatically retains ViewModel objects during configuration changes
 so that the data they hold is immediately available after the recomposition.
 */

/*
Any changes to the UI state are immediately reflected in the UI
 */
@OptIn(ExperimentalMaterialApi::class)
class HomeViewModel(private val container: AppContainer): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())

    suspend fun test() {
        //container.tasksRepository.insertTask(Task(1, "Work", 5, Frequency.Daily, 2))
    }
       // _uiState.asStateFlow()
    val taskey = container.tasksRepository.getTasks().stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5000),
    initialValue = listOf()
    )

    val homeUiState : StateFlow<HomeUiState> = combine(
        container.tasksRepository.getTaskByFrequencyList(Frequency.Daily),
        container.tasksRepository.getTaskByFrequencyList(Frequency.Weekly),
        container.tasksRepository.getTaskByFrequencyList(Frequency.Monthly),
        container.tasksRepository.getTaskByFrequencyList(Frequency.Yearly)
    ) { day, week, month, year ->
        HomeUiState(mapOf(
            Frequency.Daily to day,
            Frequency.Weekly to week,
            Frequency.Monthly to month,
            Frequency.Yearly to year)
            )}.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = HomeUiState()
            )

    fun finishTask(task: Task) {
        viewModelScope.launch {
            container.tasksRepository.updateTask(task.copy(isDone = true, streak = task.streak + 1))
        }
    }

    fun InsertTask(task: Task) {
        viewModelScope.launch {
            container.tasksRepository.insertTask(task)
        }
    }

    /*fun test(): StateFlow<List<Task>> {
        Log.e("DatabaseStuff", "Test started")
        //container.__dataBase.clearAllTables()
        //container.tasksRepository.insertTask(Task(1, "Work", 5, Frequency.Weekly, 4, Categories.Health))
        val testBadges = container.badgesRepository.getBadgeList()
        val testCategory = container.categoriesRepository.listOfAllCategoriesSortedByCurrentLevel()
        return container.tasksRepository.getTaskByFrequencyList().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = listOf()
        )

    }*/

    /*
    companion object {
        val factory:ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as OurApplication)
                HomeViewModel(application.container)
            }
        }
    }
   */




}


data class HomeUiState(
    val taskMap: Map<Frequency, List<Task>> = mapOf()
)
