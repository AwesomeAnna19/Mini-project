package com.example.mini_project.data.badge

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BadgeDao {

    /* Fra Miro: Select based on id */
    @Query("SELECT * from badges")
    fun getBadgeList(): Flow<List<Badge>>

    /* Fra Miro: Sort by done descending */
    @Query("SELECT * from badges ORDER BY isEarned DESC")
    fun getBadgesAndSortByDone(): Flow<List<Badge>>

    /* Fra Miro: Sort by stat progress descending */
    @Query("SELECT * from badges ORDER BY statValue DESC")
    fun sortBadgesByStatProgress(): Flow<List<Badge>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBadge(badge: Badge)

    @Query("SELECT * from badges WHERE isEarned = true UNION SELECT * from badges WHERE isEarned = false ORDER BY maximumProgress")
    fun getAllBadgesSortedByProgress() : Flow<List<Badge>>

    /*Fra Miro: Edit*/
    @Update
    suspend fun update(badge: Badge)

}