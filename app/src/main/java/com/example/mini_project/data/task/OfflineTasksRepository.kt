package com.example.mini_project.data.task

import com.example.mini_project.data.category.Category
import kotlinx.coroutines.flow.Flow

class OfflineTasksRepository(private val taskDao: TaskDao) : TasksRepository {
    override fun getTaskList(id: Int): Flow<List<Task>> = taskDao.getTask(id)

    override fun tasksReminderSchedulesList(id: Int): Flow<List<Task>> = taskDao.tasksReminderSchedules()

    override fun groupsOfSameTasksList(id: Int): Flow<List<Task>> = taskDao.groupsOfSameTasks()

    //override fun filterCategoriesStreaksStats(category: Category): Flow<List<Task>> = taskDao.filterCategoriesStreaks(category)

    override suspend fun insertTask(task: Task) = taskDao.insert(task)

    override suspend fun updateTask(task: Task) = taskDao.update(task)

    override suspend fun deleteTask(task: Task) = taskDao.delete(task)
}