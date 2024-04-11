package com.example.mini_project.data.badge

import androidx.compose.ui.graphics.painter.Painter
import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity annotation marks a class as a database Entity class.
// For each Entity class, the app creates a database table to hold the items.
@Entity(tableName = "badges") // tableName argument sets badges as the SQLite table name.
data class Badge (
    @PrimaryKey (autoGenerate = true) //@PrimaryKey makes id the primary key to uniquely identify every entry in Item table
    val id: Int = 0,
    val name: String,
    val description: String,

    //Få styr på de her variabelnavne og typer!
    val trackedStat: String,
    val statValue: Int,
    val maximumProgress: Int,

    val isEarned: Boolean = false,
) 