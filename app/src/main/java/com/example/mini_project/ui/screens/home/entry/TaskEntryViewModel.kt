package com.example.mini_project.ui.screens.home.entry

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import co.yml.charts.common.extensions.isNotNull
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


    fun updateUiState(taskDetails: TaskDetails) {
        taskUiState =
            TaskUiState(taskDetails = taskDetails, isEntryValid = validateTaskInput(taskDetails)).also { Log.e(null, it.isEntryValid.toString()) }
    }
    private fun validateTaskInput(uiState: TaskDetails = taskUiState.taskDetails) : Boolean {
        return with(uiState) {
            title.isNotBlank()  && category != null && frequency.isNotNull() && difficulty.isNotNull()
        }
    }

    suspend fun saveTask() {
        if (validateTaskInput()) {
            tasksRepository.insertTask(taskUiState.taskDetails.toTask())
        }
    }
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
 * Extension function to convert [TaskDetail] to [Task].
 */
fun TaskDetails.toTask(): Task = Task(
    id = id,
    title = title,
    category = Categories.valueOf(category),
    frequency = Frequency.valueOf(frequency),
    difficulty = difficulty,
    isDone = isDone,
    streak = streak
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

