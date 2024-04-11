package com.example.mini_project.data.badge

import com.example.mini_project.data.badge.BadgeDao
import com.example.mini_project.data.badge.BadgeRepository
import kotlinx.coroutines.flow.Flow

class OfflineBadgesRepository(private val badgeDao: BadgeDao) : BadgeRepository {
    override fun getBadgeList(id: Int): Flow<List<Badge>> = badgeDao.getBadge(id)

    override fun sortBadgesByDoneList(isEarned: Boolean): Flow<List<Badge>> = badgeDao.getBadge(isEarned)

    override fun sortBadgesByStatProgress(maximumProgress: Int): Flow<List<Badge>> = badgeDao.sortBadgesByStatProgress(maximumProgress)

    override suspend fun updateBadge(badge: Badge) = badgeDao.update(badge)
}