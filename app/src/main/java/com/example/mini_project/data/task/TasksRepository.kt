package com.example.mini_project.data.task

import kotlinx.coroutines.flow.Flow

interface TasksRepository {

    fun getTaskList(): Flow<List<Task>>

    fun tasksReminderSchedulesList(): Flow<List<Task>>

    fun groupsOfSameTasksList(): Flow<List<Task>>

    fun filterCategoriesStreaksStats(): Flow<List<Task>>

    /* Inserts/adds a task in the data source */
    suspend fun insertTask(task: Task)

    /* Deletes a task from the data source */
    suspend fun deleteTask(task: Task)

    /* Updates a task in the data source */
    suspend fun updateTask(task: Task)

}