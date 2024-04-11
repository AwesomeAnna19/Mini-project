package com.example.mini_project.data.category

import com.example.mini_project.data.task.Task
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {

    fun getCategoryList(name: String): Flow<List<Category>>

    fun categoryReminderSchedulesList(name: String): Flow<List<Category>>

}