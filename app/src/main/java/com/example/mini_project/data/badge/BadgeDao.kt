package com.example.mini_project.data.badge

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BadgeDao {

    /* Fra Miro: Select based on id */
    @Query("SELECT * from badges WHERE id = :id")
    fun getBadge(id: Int): Flow<List<Badge>>

    /* Fra Miro: Sort by done descending */
    @Query("SELECT * from badges ORDER BY isEarned DESC")
    fun sortBadgesByDone(): Flow<List<Badge>>

    /* Fra Miro: Sort by stat progress descending */
    @Query("SELECT * from badges ORDER BY statValue = :statValue DESC")
    fun sortBadgesByStatProgress(statValue: Int): Flow<List<Badge>>

    /*Fra Miro: Edit*/
    @Update
    suspend fun update(badge: Badge)

}