package com.example.mini_project.ui.screens.home.entry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mini_project.data.task.Categories
import com.example.mini_project.data.task.Frequency
import com.example.mini_project.data.task.Task
import com.example.mini_project.data.task.TasksRepository

/**
* To validate and insert task to the Room database.
* */
class TaskEntryViewModel (private val tasksRepository: TasksRepository ) : ViewModel() {

    var taskUiState by mutableStateOf(TaskUiState())
        private set
}
data class TaskUiState(
    val taskDetails: TaskDetails = TaskDetails(),
    val isEntryValid: Boolean = false
)

data class TaskDetails(
    val id: Int = 0,
    val title: String = "",
    val category: String = "",
    val frequency: String = "",
    val difficulty: Int = 0,
    val isDone: Boolean = false,
    val streak: Int = 0
)

/**
 * Extension function to convert [Task] to [TaskUiState]
 */
fun Task.toTaskUiState(isEntryValid: Boolean = false): TaskUiState = TaskUiState(
    taskDetails = this.toTaskDetails(),
    isEntryValid = isEntryValid
)

/**
 * Extension function to convert [Task] of type Task to [TaskDetails] of type TaskDetails
 */
fun Task.toTaskDetails(): TaskDetails = TaskDetails(
    id = id,
    title = title,
    category = category.toString(),
    frequency = frequency.toString(),
    difficulty = difficulty,
    isDone = isDone,
    streak = streak
)

fun TaskDetails.toTask(): Task = Task(
    id = id,
    title = title,
    category = Categories.valueOf(category),
    frequency = Frequency.valueOf(frequency),
    difficulty = difficulty,
    isDone = isDone,
    streak = streak
)
