package com.example.mini_project.data.task

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    /*Fra Miro: Select all based on id -> instans af klassen spyttes ud*/
    @Query("SELECT * from tasks WHERE id = :id")
    fun getTask(id: Int): Flow<Task>

    /*Fra Miro: Select og group by reminder schedule*/
    @Query("SELECT * from tasks ORDER BY frequency ASC")
    fun getTasksByFrequency(): Flow<List<Task>>


    /*Fra Miro: filter based on category*/
    @Query("SELECT * from tasks WHERE category = :category")
    fun getTasksByCategory(category: Categories): Flow<List<Task>>

    @Query("SELECT * from tasks WHERE category = :category ORDER BY streak ASC LIMIT 5")
    fun getFiveTasksByCategoryAndSortByStreak(category: Categories): Flow<List<Task>>

    /*Fra Miro: Add*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    /*Fra Miro: Edit*/
    @Update
    suspend fun update(task: Task)

    /*Fra Miro: Delete*/
    @Delete
    suspend fun delete(task: Task)

}
