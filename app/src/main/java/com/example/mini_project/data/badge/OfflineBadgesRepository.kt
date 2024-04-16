package com.example.mini_project.data.badge

import kotlinx.coroutines.flow.Flow

class OfflineBadgesRepository(private val badgeDao: BadgeDao) : BadgesRepository {
    override fun getBadgeList(): Flow<List<Badge>> = badgeDao.getBadgeList()

    override fun sortBadgesByDoneList(): Flow<List<Badge>> = badgeDao.sortBadgesByDone()

    override fun sortBadgesByStatProgress(): Flow<List<Badge>> = badgeDao.sortBadgesByStatProgress()

    override suspend fun updateBadge(badge: Badge) = badgeDao.update(badge)

    override suspend fun insertBadge(badge: Badge) = badgeDao.insertBadge(badge)
}