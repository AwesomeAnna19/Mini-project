package com.example.mini_project.data

import java.util.concurrent.Flow

interface TaskRepository {

    fun getTaskList(): Flow<List<Task>>

    fun tasksReminderSchedulesList(): Flow<List<Task>>

    fun groupsOfSameTasksList(): Flow<List<Task>>

    fun filterCategoriesStreaksStats(): Flow<List<Task>>

    /* Inserts/adds an item/task in the data source */
    suspend fun insertItem(item: Task)

    /* Deletes an item/task from the data source */
    suspend fun deleteItem(item: Task)

    /* Updates an item/task in the data source */
    suspend fun updateItem(item: Task)

}