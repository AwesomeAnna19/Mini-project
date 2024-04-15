package com.example.mini_project.data.badge

import kotlinx.coroutines.flow.Flow

interface BadgesRepository {

    fun getBadgeList(): Flow<List<Badge>>

    fun sortBadgesByDoneList(): Flow<List<Badge>>

    fun sortBadgesByStatProgress(): Flow<List<Badge>>

    suspend fun updateBadge(badge: Badge)

    suspend fun insertBadge(badge: Badge)
}