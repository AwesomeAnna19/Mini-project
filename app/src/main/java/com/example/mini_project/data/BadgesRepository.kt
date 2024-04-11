package com.example.mini_project.data

import kotlinx.coroutines.flow.Flow

interface BadgesRepository {

    fun getBadgeList(id: Int): Flow<List<Badge>>

    fun sortBadgesByDoneList(isEarned: Boolean): Flow<List<Badge>>

    fun sortBadgesByStatProgress(maximumProgress: Int): Flow<List<Badge>>

    suspend fun updateBadge(badge: Badge)
}