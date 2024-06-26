package com.example.mini_project.ui.screens.home.details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.isNotNull
import com.example.mini_project.data.AppContainer
import com.example.mini_project.data.task.Categories
import com.example.mini_project.data.task.TasksRepository
import com.example.mini_project.ui.screens.home.entry.TaskDetails
import com.example.mini_project.ui.screens.home.entry.TaskUiState
import com.example.mini_project.ui.screens.home.entry.toTask
import com.example.mini_project.ui.screens.home.entry.toTaskDetails
import com.example.mini_project.ui.screens.home.entry.toTaskUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val tasksRepository: TasksRepository
) : ViewModel() {


    /**
     * Holds current item ui state
     */
    val taskUiState = MutableStateFlow(TaskUiState())

    private val taskId: Int = checkNotNull(savedStateHandle[TaskDetailsRoute.taskIdArgument])

//fra itemdetailsviewmodel
    val uiState: StateFlow<TaskDetails> =
        tasksRepository.getTask(taskId)
            .filterNotNull()
            .map {
                it.toTaskDetails()
            }.stateIn(
                scope = viewModelScope ,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = TaskDetails()
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
    ///


    init {
        viewModelScope.launch {
            taskUiState.value = tasksRepository.getTask(taskId)
                .filterNotNull()
                .first()
                .toTaskUiState(isEntryValid = true)
        }
    }

    private fun validateTaskInput(uiState: TaskDetails = taskUiState.value.taskDetails) : Boolean {
        return with(uiState) {
            title.isNotBlank() && category.isNotBlank() && frequency.isNotBlank() && difficulty.isNotNull()
        }
    }

    fun updateUiState(taskDetail: TaskDetails) {

        taskUiState.update { it.copy(taskDetails = taskDetail, isEntryValid = validateTaskInput(taskDetail)) }

    }

    suspend fun deleteTask() {
        tasksRepository.deleteTask(taskUiState.value.taskDetails.toTask())

    }

    suspend fun updateTask() {
        if (validateTaskInput(taskUiState.value.taskDetails)) {
            tasksRepository.updateTask(taskUiState.value.taskDetails.toTask())
        }
    }

}




