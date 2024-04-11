package com.example.mini_project.data

import com.example.mini_project.data.category.CategoriesRepository
import com.example.mini_project.data.category.Category
import com.example.mini_project.data.category.CategoryDao
import kotlinx.coroutines.flow.Flow

class OfflineCategoriesRepository(private val categoryDao: CategoryDao) : CategoriesRepository {
    override fun getCategoryList(name: String): Flow<List<Category>> = categoryDao.getCategory(name)

    override fun categoryReminderSchedulesList(name: String): Flow<List<Category>> = categoryDao.categoryReminderSchedules(name)
}