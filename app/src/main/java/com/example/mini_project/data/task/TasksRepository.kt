package com.example.mini_project.data.task

import com.example.mini_project.data.category.Category
import kotlinx.coroutines.flow.Flow

interface TasksRepository {

    fun getTaskList(id: Int): Flow<List<Task>>

    fun tasksReminderSchedulesList(id: Int): Flow<List<Task>>

    fun groupsOfSameTasksList(id: Int): Flow<List<Task>>

    fun filterCategoriesStreaksStats(category: Category): Flow<List<Task>>

    /* Inserts/adds a task in the data source */
    suspend fun insertTask(task: Task)

    /* Deletes a task from the data source */
    suspend fun deleteTask(task: Task)

    /* Updates a task in the data source */
    suspend fun updateTask(task: Task)

}