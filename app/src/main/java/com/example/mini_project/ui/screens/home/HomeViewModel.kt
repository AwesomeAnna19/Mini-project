package com.example.mini_project.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.mini_project.data.AppContainer
import com.example.mini_project.data.task.Frequency
import com.example.mini_project.data.task.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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

class HomeViewModel(private val container: AppContainer): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())


    val homeUiState: StateFlow<HomeUiState> =
        _uiState.asStateFlow()

    suspend fun test() {
        container.tasksRepository.insertTask(Task(1, "Work", 5, Frequency.Daily, 2))
    }

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
    val taskList: List<Task> = listOf(),
)