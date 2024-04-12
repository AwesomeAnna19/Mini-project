package com.example.mini_project.data.category

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mini_project.data.task.Categories

//@Entity annotation marks a class as a database Entity class.
// For each Entity class, the app creates a database table to hold the items.
@Entity(tableName = "categories") // tableName argument sets tasks as the SQLite table name.
data class Category (
    @PrimaryKey() //@PrimaryKey makes name the primary key to uniquely identify every entry in Item table
    val name: Categories,
    val color: String,
    val currentLevel: Int,
    val currentXp: Int,
    val xpRequiredForLevelUp: Int
)