package com.example.mini_project.data

import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getTaskList(id: Int): Flow<List<Task>>

    fun tasksReminderSchedulesList(id: Int): Flow<List<Task>>

    fun groupsOfSameTasksList(id: Int): Flow<List<Task>>

    fun filterCategoriesStreaksStats(category: Category): Flow<List<Task>>

    /* Inserts/adds an item/task in the data source */
    suspend fun insertItem(item: Task)

    /* Deletes an item/task from the data source */
    suspend fun deleteItem(item: Task)

    /* Updates an item/task in the data source */
    suspend fun updateItem(item: Task)

}