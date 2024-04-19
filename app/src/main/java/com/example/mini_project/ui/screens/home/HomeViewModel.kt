package com.example.mini_project.ui.screens.home

import android.util.Log
import androidx.compose.material.BottomSheetState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mini_project.data.SavedDateDataStore
import com.example.mini_project.data.category.CategoriesRepository
import com.example.mini_project.data.category.Category
import com.example.mini_project.data.task.Frequency
import com.example.mini_project.data.task.Task
import com.example.mini_project.data.task.TasksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import java.util.Calendar


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
class HomeViewModel(private val tasksRepository: TasksRepository, private val categoryRepository: CategoriesRepository, private val savedDateDataStore: SavedDateDataStore): ViewModel() {

    val windowUiState = MutableStateFlow(HomeWindowUiState())

    val homeUiState : StateFlow<HomeUiState> = combine(
        tasksRepository.getTaskByFrequencyList(Frequency.Daily),
        tasksRepository.getTaskByFrequencyList(Frequency.Weekly),
        tasksRepository.getTaskByFrequencyList(Frequency.Monthly),
        tasksRepository.getTaskByFrequencyList(Frequency.Yearly)
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
    fun CheckTime() {

    viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val time = Calendar.getInstance()

            val newDate = arrayOf(
                time.get(Calendar.DAY_OF_MONTH),
                time.get(Calendar.WEEK_OF_YEAR),
                time.get(Calendar.MONTH),
                time.get(Calendar.YEAR)
            )

            val savedDates = savedDateDataStore.SavedDate.first()
            val frequenciesToRenew = arrayOf(false, false, false, false)
            savedDates.forEachIndexed { i, d ->
                if (d != newDate[i]) {
                    frequenciesToRenew[i] = true
                }

                if (frequenciesToRenew.contains(true)) {
                    savedDateDataStore.SaveDate(newDate[0], newDate[1], newDate[2], newDate[3])
                }

            }

            frequenciesToRenew.forEachIndexed { i, r ->
                if (r) {
                    homeUiState.value.taskMap[Frequency.entries[i]]?.forEach {
                        if (it.isDone) setFinishTask(it, false)
                        else resetTaskStreak(it)
                    }
                }
            }
        }


    }

    }


    fun setFinishTask(task: Task, status: Boolean = true) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                tasksRepository.updateTask(task.copy(isDone = status, streak = task.streak + 1))
                val relevantCat = categoryRepository.getCategoryFromName(task.category).first()
                if (relevantCat == null) {return@withContext}
                var newXp = relevantCat.currentXp + task.difficulty
                if (newXp >= relevantCat.xpRequiredForLevelUp) {
                    newXp -= relevantCat.xpRequiredForLevelUp
                    categoryRepository.updateCategory(
                        relevantCat.copy(
                            currentXp = newXp,
                            currentLevel = relevantCat.currentLevel + 1
                        )
                    )
                } else categoryRepository.updateCategory(relevantCat.copy(currentXp = newXp))
            }
        }
    }

    fun resetTaskStreak(task: Task) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                tasksRepository.updateTask(task.copy(streak = 0))
            }
        }
    }

    fun InsertTask(task: Task) {
        viewModelScope.launch {
            tasksRepository.insertTask(task)
        }
    }

    fun InsertCategory(category : Category) {
        viewModelScope.launch {
            categoryRepository.insertCategory(category)
        }
    }

    fun toggleFABToState(state: Boolean) {
        windowUiState.update { i -> i.copy(showFAB = state) }
    }
}


data class HomeUiState(
    val taskMap: Map<Frequency, List<Task>> = mapOf()
)

data class HomeWindowUiState(
    val showFAB: Boolean = true,
    val sheetHidden: Boolean = true

)