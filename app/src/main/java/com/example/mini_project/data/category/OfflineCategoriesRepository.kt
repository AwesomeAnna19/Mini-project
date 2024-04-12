package com.example.mini_project.data.category

import com.example.mini_project.data.task.Categories
import kotlinx.coroutines.flow.Flow

class OfflineCategoriesRepository(private val categoryDao: CategoryDao) : CategoriesRepository {
    override fun getCategoryList(name: Categories): Flow<Categories> = categoryDao.getCategory(name)

    override fun listOfAllCategoriesSortedByCurrentLevel(): Flow<List<Categories>> = categoryDao.listOfAllCategoriesSortedByCurrentLevel()

    override suspend fun updateCategories(name: Categories) = categoryDao.update(name)
}