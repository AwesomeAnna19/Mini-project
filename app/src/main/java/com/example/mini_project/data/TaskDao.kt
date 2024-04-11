package com.example.mini_project.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    /*Fra Miro: Select all based on id -> instans af klassen spyttes ud*/
    @Query("SELECT * from tasks WHERE id = :id")
    fun getTask(id: Int):Task

    /*Fra Miro: Select og group by reminder schedule*/
    @Query("SELECT SUM (id) from tasks GROUP BY id")
    fun tasksReminderSchedules(id: Int):Task

    /*Fra Miro: Sort based on id ascending eller titel*/
    @Query("SELECT * from tasks ORDER BY id ASC")
    fun groupsOfSameTasks(id: Int):Task

    /*Fra Miro: filter based on category*/
    @Query("SELECT * from tasks WHERE category = :category")
    fun filterCategoriesStreaks(category: Category):Category

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
