package com.example.mini_project.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.mini_project.data.task.Task
import com.example.mini_project.data.task.TasksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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


data class HomeUiState(
    val taskList: List<Task> = listOf()
)