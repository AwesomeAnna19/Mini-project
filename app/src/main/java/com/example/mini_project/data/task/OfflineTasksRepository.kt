package com.example.mini_project.data.task

import kotlinx.coroutines.flow.Flow

class OfflineTasksRepository(private val taskDao: TaskDao) : TaskRepository {
    override fun getTaskList(id: Int): Flow<List<Task>> = taskDao.getTask(id)

    override fun tasksReminderSchedulesList(id: Int): Flow<List<Task>> = taskDao.tasksReminderSchedules(id)

    override fun groupsOfSameTasksList(id: Int): Flow<List<Task>> = taskDao.groupsOfSameTasks(id)

    override fun filterCategoriesStreaksStats(category: Category): Flow<List<Task>> = taskDao.filterCategoriesStreaks(category)

    override suspend fun insertItem(task: Task) = taskDao.insert(task)

    override suspend fun updateItem(task: Task) = taskDao.update(task)

    override suspend fun deleteItem(task: Task) = taskDao.delete(task)
}