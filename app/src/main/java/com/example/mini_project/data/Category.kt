package com.example.mini_project.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity annotation marks a class as a database Entity class.
// For each Entity class, the app creates a database table to hold the items.
@Entity(tableName = "categories") // tableName argument sets tasks as the SQLite table name.
data class Category (
    @PrimaryKey() //@PrimaryKey makes name the primary key to uniquely identify every entry in Item table
    val name: String,
    val color: String,
    val currentLevel: Int,
    val currentXp: Int,
    val xpRequiredForLevelUp: Int
)