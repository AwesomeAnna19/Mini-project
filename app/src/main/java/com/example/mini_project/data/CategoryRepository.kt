package com.example.mini_project.data

import androidx.room.Dao
import java.util.concurrent.Flow

@Dao
interface CategoryRepository {

    fun getCategoryList(): Flow<List<Task>>

    fun categoryReminderSchedulesList(): Flow<List<Task>>

}