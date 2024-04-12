package com.example.mini_project.data.badge

import kotlinx.coroutines.flow.Flow

class OfflineBadgesRepository(private val badgeDao: BadgeDao) : BadgesRepository {
    override fun getBadgeList(id: Int): Flow<List<Badge>> = badgeDao.getBadge(id)

    override fun sortBadgesByDoneList(): Flow<List<Badge>> = badgeDao.getBadgesAndSortByDone()

    override fun sortBadgesByStatProgress(maximumProgress: Int): Flow<List<Badge>> = badgeDao.sortBadgesByStatProgress(maximumProgress)

    override suspend fun updateBadge(badge: Badge) = badgeDao.update(badge)
}