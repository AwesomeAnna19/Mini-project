package com.example.mini_project.data

import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getCategoryList(name: String): Flow<List<Category>>

    fun categoryReminderSchedulesList(name: String): Flow<List<Category>>

}