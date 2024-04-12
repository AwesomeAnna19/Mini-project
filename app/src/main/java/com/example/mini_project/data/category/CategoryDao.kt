package com.example.mini_project.data.category

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.mini_project.data.task.Categories
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    /*Fra Miro: Select all based on id -> instans af klassen spyttes ud*/
    @Query("SELECT * from categories WHERE name = :name")
    fun getCategory(name: Categories): Flow<Categories>

    /*Fra Miro: Get a list of all categories that is sorted by their current level
    -> so the category with the highest level is at the top of the list*/
    @Query("SELECT * from categories ORDER BY currentLevel DESC")
    fun listOfAllCategoriesSortedByCurrentLevel(): Flow<List<Categories>>

    /*Fra Miro: Update the specific task with a specific category*/
    @Update
    suspend fun update(name: Categories)

}