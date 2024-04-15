package com.example.mini_project.data.category

import com.example.mini_project.data.task.Categories
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {

    fun listOfAllCategoriesSortedByCurrentLevel(): Flow<List<Category>>

    fun listOfAllCategoriesSortedByCurrentLevel(take : Int): Flow<List<Category>>

    suspend fun updateCategory(name: Category)

    suspend fun insertCategory(category: Category)

}