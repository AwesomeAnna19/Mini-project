package com.example.mini_project.data.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mini_project.data.task.Categories
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    /*Fra Miro: Select og group by reminder schedule*/
    /*Fra Miro: Get a list of all categories that is sorted by their current level
    -> so the category with the highest level is at the top of the list*/
    @Query("SELECT * from categories ORDER BY currentLevel DESC, currentXp/xpRequiredForLevelUp")
    fun listOfAllCategoriesSortedByCurrentLevel(): Flow<List<Category>>

    @Query("SELECT * from categories ORDER BY currentLevel DESC, currentXp/xpRequiredForLevelUp LIMIT :take")
    fun listOfAllCategoriesSortedByCurrentLevel(take : Int): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE name = :name")
    fun getCategoryFromName(name : Categories) : Flow<Category>


    /*Fra Miro: Update the specific task with a specific category*/
    @Update
    suspend fun update(category: Category)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)

}