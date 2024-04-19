package com.example.mini_project.data.category

import com.example.mini_project.data.task.Categories
import kotlinx.coroutines.flow.Flow

class OfflineCategoriesRepository(private val categoryDao: CategoryDao) : CategoriesRepository {

    override fun listOfAllCategoriesSortedByCurrentLevel(): Flow<List<Category>> = categoryDao.listOfAllCategoriesSortedByCurrentLevel()

    override fun listOfAllCategoriesSortedByCurrentLevel(take : Int): Flow<List<Category>> = categoryDao.listOfAllCategoriesSortedByCurrentLevel(take)

    override fun getCategoryFromName(name: Categories): Flow<Category> = categoryDao.getCategoryFromName(name)

    override suspend fun updateCategory(category: Category) = categoryDao.update(category)

    override suspend fun insertCategory(category: Category) = categoryDao.insert(category)

}