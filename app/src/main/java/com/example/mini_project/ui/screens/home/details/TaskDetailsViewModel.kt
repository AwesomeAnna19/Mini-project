package com.example.mini_project.ui.screens.home.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.mini_project.data.AppContainer

class TaskDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    //private val container: AppContainer
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
