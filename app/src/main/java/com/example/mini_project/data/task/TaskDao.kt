package com.example.mini_project.data.task

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mini_project.data.category.Category
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




    /*Fra Miro: Select all based on id -> instans af klassen spyttes ud*/
    @Query("SELECT * from tasks WHERE id = :id")
    fun getTask(id: Int): Flow<List<Task>>

    /*Fra Miro: Select og group by reminder schedule*/
    @Query("SELECT SUM (id) from tasks GROUP BY id")
    fun tasksReminderSchedules(id: Int): Flow<List<Task>>

    /*Fra Miro: Sort based on id ascending eller titel*/
    @Query("SELECT * from tasks ORDER BY id ASC")
    fun groupsOfSameTasks(id: Int): Flow<List<Task>>

    /*Fra Miro: filter based on category*/
    @Query("SELECT * from tasks WHERE category = :category")
    fun filterCategoriesStreaks(category: Category): Flow<List<Task>>


}
