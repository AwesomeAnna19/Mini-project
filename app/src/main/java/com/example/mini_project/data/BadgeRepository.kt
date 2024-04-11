package com.example.mini_project.data

import kotlinx.coroutines.flow.Flow

interface BadgeRepository {

    fun getBadgeList(): Flow<List<Badge>>

    fun sortBadgesByDoneList(): Flow<List<Badge>>

    fun sortBadgesByStatProgress(): Flow<List<Badge>>

    suspend fun updateBadge(badge: Badge)
}