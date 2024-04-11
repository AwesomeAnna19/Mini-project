package com.example.mini_project.data

import com.example.mini_project.data.badge.Badge
import kotlinx.coroutines.flow.Flow

class OfflineBadgesRepository(private val badgeDao: BadgeDao) : BadgesRepository {
    override fun getBadgeList(id: Int): Flow<List<Badge>> = badgeDao.getBadge(id)

    override fun sortBadgesByDoneList(isEarned: Boolean): Flow<List<Badge>> = badgeDao.getBadge(isEarned)

    override fun sortBadgesByStatProgress(maximumProgress: Int): Flow<List<Badge>> = badgeDao.sortBadgesByStatProgress(maximumProgress)

    override suspend fun updateBadge(badge: Badge) = badgeDao.update(badge)
}