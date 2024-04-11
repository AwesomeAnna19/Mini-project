package com.example.mini_project.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update

@Dao
interface BadgeDao {

    /* Fra Miro: Select based on id */
    @Query("SELECT * from badges WHERE id = :id")
    fun getBadge(id: Int):Badge

    /* Fra Miro: Sort by done descending */
    @Query("SELECT * from badges ORDER BY isEarned = :isEarned DESC")
    fun sortBadgesByDone(isEarned: Boolean):Badge

    /* Fra Miro: Sort by stat progress descending */
    @Query("SELECT * from badges ORDER BY maximumProgress = :maximumProgress DESC")
    fun sortBadgesByStatProgress(maximumProgress: Int):Badge

    /*Fra Miro: Edit*/
    @Update
    suspend fun update(badge: Badge)

}