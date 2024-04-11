package com.example.mini_project.data.category

import androidx.room.Dao
import androidx.room.Query
import com.example.mini_project.data.category.Category

@Dao
interface CategoryDao {

    /*Fra Miro: Select all based on id -> instans af klassen spyttes ud*/
    @Query("SELECT * from categories WHERE name = :name")
    fun getCategory(name: String): Category

    /*Fra Miro: Select og group by reminder schedule*/
    @Query("SELECT SUM (name) from categories GROUP BY name")
    fun categoryReminderSchedules(name: String): Category

}