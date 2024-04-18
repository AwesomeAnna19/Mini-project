package com.example.mini_project.data.task

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mini_project.data.category.Category

//@Entity annotation marks a class as a database Entity class.
// For each Entity class, the app creates a database table to hold the items.
@Entity(tableName = "tasks") // tableName argument sets tasks as the SQLite table name.
data class Task (
    @PrimaryKey(autoGenerate = true) //@PrimaryKey makes id the primary key to uniquely identify every entry in Item table
    val id: Int = 0,
    val title: String,
    val difficulty: Int,     //Range ?
    val frequency: Frequency,
    val streak: Int,         //Times done in a row
    val category: Categories,
    val isDone: Boolean
)

enum class  Frequency {

    Daily,
    Weekly,
    Monthly,
    Yearly

}

enum class Categories {

    Hobbies,
    Health,
    Social

}


