package com.example.mini_project.data

import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getCategoryList(): Flow<List<Task>>

    fun categoryReminderSchedulesList(): Flow<List<Task>>

}