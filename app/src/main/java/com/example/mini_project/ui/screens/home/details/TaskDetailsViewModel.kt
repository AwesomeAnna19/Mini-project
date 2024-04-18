package com.example.mini_project.ui.screens.home.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.mini_project.data.AppContainer
import com.example.mini_project.data.task.TasksRepository

class TaskDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val tasksRepository: TasksRepository
) : ViewModel() {


    /**
     * Holds current item ui state
     */


    private val taskId: Int = checkNotNull(savedStateHandle[TaskDetailsRoute.taskIdArgument])


}

/*
data class TaskDetailsUiState(
    val taskDetail

)
*/
