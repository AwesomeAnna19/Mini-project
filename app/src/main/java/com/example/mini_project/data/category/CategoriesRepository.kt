package com.example.mini_project.data.category

import com.example.mini_project.data.task.Task
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {

    fun getCategoryList(): Flow<List<Task>>

    fun categoryReminderSchedulesList(): Flow<List<Task>>

}