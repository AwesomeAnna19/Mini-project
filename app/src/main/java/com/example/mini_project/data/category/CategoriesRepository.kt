package com.example.mini_project.data.category

import com.example.mini_project.data.task.Categories
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {

    fun getCategoryList(name: Categories): Flow<Categories>

    fun listOfAllCategoriesSortedByCurrentLevel(): Flow<List<Categories>>

    suspend fun updateCategories(name: Categories)

}