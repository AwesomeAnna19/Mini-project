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

    //To insert a task into the tasks table
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    //To update a task in the tasks table
    @Update
    suspend fun update(task: Task)

    //To delete a task from the tasks table
    @Delete
    suspend fun delete(task: Task)




    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTask(id: Int): Flow<Task>

    /*Fra Miro: Select all based on id -> instans af klassen spyttes ud*/
    @Query("SELECT * from tasks order by streak desc")
    fun getTasks(): Flow<List<Task>>

    /*Fra Miro: Select og group by reminder schedule*/
    @Query("SELECT * from tasks Where frequency = :frequency order by title asc")
    fun getTasksByFrequency(frequency: Frequency): Flow<List<Task>>


    /*Fra Miro: filter based on category*/
    @Query("SELECT * from tasks WHERE category = :category")
    fun getTasksByCategory(category: Categories): Flow<List<Task>>

    @Query("SELECT * from tasks WHERE category = :category ORDER BY streak ASC LIMIT 5")
    fun getFiveTasksByCategoryAndSortByStreak(category: Categories): Flow<List<Task>>


}
