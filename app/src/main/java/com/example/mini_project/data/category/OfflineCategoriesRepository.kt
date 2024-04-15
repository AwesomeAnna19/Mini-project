package com.example.mini_project.data.category

import kotlinx.coroutines.flow.Flow

class OfflineCategoriesRepository(private val categoryDao: CategoryDao) : CategoriesRepository {
<<<<<<< Updated upstream
    override fun getCategoryList(name: String): Flow<List<Category>> = categoryDao.getCategory(name)

    //override fun categoryReminderSchedulesList(name: String): Flow<List<Category>> = categoryDao.categoryReminderSchedules(name)
=======
    override fun getCategory(category: Categories): Flow<Category> = categoryDao.getCategory(category)

    override fun listOfAllCategoriesSortedByCurrentLevel(): Flow<List<Category>> = categoryDao.listOfAllCategoriesSortedByCurrentLevel()

    override fun listOfAllCategoriesSortedByCurrentLevel(take : Int): Flow<List<Category>> = categoryDao.listOfAllCategoriesSortedByCurrentLevel(take)
    override suspend fun updateCategory(category: Category) = categoryDao.update(category)

    override suspend fun insertCategory(category: Category) = categoryDao.insert(category)
>>>>>>> Stashed changes
}