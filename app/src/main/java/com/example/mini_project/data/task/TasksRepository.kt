package com.example.mini_project.data.task

import com.example.mini_project.data.category.Category
import kotlinx.coroutines.flow.Flow

interface TasksRepository {

    fun getTasks(): Flow<List<Task>>

    fun getTaskByFrequencyList(frequency: Frequency): Flow<List<Task>>

    fun filterCategoriesStreaksStats(category: Categories): Flow<List<Task>>

    /* Inserts/adds a task in the data source */
    suspend fun insertTask(task: Task)

    /* Deletes a task from the data source */
    suspend fun deleteTask(task: Task)

    /* Updates a task in the data source */
    suspend fun updateTask(task: Task)

}