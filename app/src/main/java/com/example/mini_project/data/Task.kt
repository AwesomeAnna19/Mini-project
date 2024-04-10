package com.example.mini_project.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity annotation marks a class as a database Entity class.
// For each Entity class, the app creates a database table to hold the items.
@Entity(tableName = "tasks") // tableName argument sets tasks as the SQLite table name.
data class Task (
    @PrimaryKey(autoGenerate = true) //@PrimaryKey makes id the primary key to uniquely identify every entry in Item table
    val id: Int = 0,
    val title: String,
    val category: Category,
    val difficulty: Int,
    val dueDate: String
)



