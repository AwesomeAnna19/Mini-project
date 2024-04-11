package com.example.mini_project.data

import android.content.Context
// import com.example.mini_project.data.category.CategoriesRepository
// import com.example.mini_project.data.task.TasksRepository

/**
 * App container for Dependency injection - best practices
 */
interface AppContainer {
    //val tasksRepository: TasksRepository
    // val categoriesRepository: CategoriesRepository
    //val badgesRepository: BadgesRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]

class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */

    /**
    override val tasksRepository: TasksRepository by lazy {
        OfflineTasksRepository(OurDatabase.getDatabase(context).taskDao())
    }

    override categoriesRepository: CategoriesRepository by lazy {
        OfflineCategoriesRepository(OurDatabase.getDatabase(context).categoryDao())
    }

    override badgesRepository: BadgesRepository by lazy {
       OfflineBadgesRepository(OurDatabase.getDatabase(context).badgeDao())
    }

   */




}
 */