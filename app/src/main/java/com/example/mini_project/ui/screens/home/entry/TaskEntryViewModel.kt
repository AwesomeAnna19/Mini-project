package com.example.mini_project.ui.screens.home.entry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mini_project.data.task.TasksRepository

/**
* To validate and insert task to the Room database.
* */
class TaskEntryViewModel (private val tasksRepository: TasksRepository ) : ViewModel() {

//    var taskUiState by mutableStateOf(())
     //   private set

}

data class TaskUiState(
    val taskDetails: String
)