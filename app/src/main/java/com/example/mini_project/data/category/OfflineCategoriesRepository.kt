package com.example.mini_project.data.category

import kotlinx.coroutines.flow.Flow

class OfflineCategoriesRepository(private val categoryDao: CategoryDao) : CategoriesRepository {
    override fun getCategoryList(name: String): Flow<List<Category>> = categoryDao.getCategory(name)

    override fun categoryReminderSchedulesList(name: String): Flow<List<Category>> = categoryDao.categoryReminderSchedules(name)
}