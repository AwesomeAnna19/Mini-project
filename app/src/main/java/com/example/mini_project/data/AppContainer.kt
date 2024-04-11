package com.example.mini_project.data

import android.content.Context
import com.example.mini_project.data.badge.BadgesRepository
import com.example.mini_project.data.badge.OfflineBadgesRepository
import com.example.mini_project.data.category.CategoriesRepository
import com.example.mini_project.data.category.OfflineCategoriesRepository
import com.example.mini_project.data.task.OfflineTasksRepository
import com.example.mini_project.data.task.TasksRepository

/**
 * App container for Dependency injection.
 * Defines a contract for managing dependencies in the app.
 * Declares abstract properties representing the repositories.
 * TasksRepository, categoriesRepository, and badgesRepository.
 * These must be included in the implementation.
 */
interface AppContainer {
    //Abstract properties
    val tasksRepository: TasksRepository
    val categoriesRepository: CategoriesRepository
    val badgesRepository: BadgesRepository
}

/**
 * The implementation of the [AppContainer] interface.
 * Provides instances of the 3 offline repositories.
 */
class AppDataContainer(private val context: Context) : AppContainer {

    /*The dependencies are initialised lazily with "by lazy" delegate
    * Meaning, they are only created when accessed for the first time.

    * Inside the lambda, creates an instance of specific Offline Repository. (task, category, badge)
    * This repository instance is constructed with the appropriate DAO.
    * The DAO is obtained from the OurDatabase singleton,
    * which is accessed via the context parameter passed the AppDataContainer.
    */
    override val tasksRepository: TasksRepository by lazy {
        OfflineTasksRepository(OurDatabase.getDatabase(context).taskDao())
    }

    override val categoriesRepository: CategoriesRepository by lazy{
        OfflineCategoriesRepository(OurDatabase.getDatabase(context).categoryDao())
    }

    override val badgesRepository: BadgesRepository by lazy {
        OfflineBadgesRepository(OurDatabase.getDatabase(context).badgeDao())
    }
}