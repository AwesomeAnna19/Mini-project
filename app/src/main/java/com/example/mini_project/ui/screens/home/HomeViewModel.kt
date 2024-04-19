package com.example.mini_project.ui.screens.home

import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mini_project.data.task.Frequency
import com.example.mini_project.data.task.Task
import com.example.mini_project.data.task.TasksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


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
class HomeViewModel(private val tasksRepository: TasksRepository): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())

    suspend fun test() {
        //container.tasksRepository.insertTask(Task(1, "Work", 5, Frequency.Daily, 2))
    }
       // _uiState.asStateFlow()
    val taskey = tasksRepository.getTasks().stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5000),
    initialValue = listOf()
    )

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

    fun finishTask(task: Task) {
        viewModelScope.launch {
            tasksRepository.updateTask(task.copy(isDone = true, streak = task.streak + 1))
        }
    }

    fun InsertTask(task: Task) {
        viewModelScope.launch {
            tasksRepository.insertTask(task)
        }
    }

}


data class HomeUiState(
    val taskMap: Map<Frequency, List<Task>> = mapOf()
)
