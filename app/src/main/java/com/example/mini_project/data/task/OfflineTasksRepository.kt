package com.example.mini_project.data.task

import kotlinx.coroutines.flow.Flow

class OfflineTasksRepository(private val taskDao: TaskDao) : TasksRepository {

    override fun getTask(id: Int): Flow<Task?> = taskDao.getTask(id)

    override fun getTasks(): Flow<List<Task>> = taskDao.getTasks()

    override fun getTaskByFrequencyList(frequency: Frequency): Flow<List<Task>> = taskDao.getTasksByFrequency(frequency)

    override fun filterCategoriesStreaksStats(category: Categories): Flow<List<Task>> = taskDao.getTasksByCategory(category)

    override suspend fun insertTask(task: Task) = taskDao.insert(task)

    override suspend fun updateTask(task: Task) = taskDao.update(task)

    override suspend fun deleteTask(task: Task) = taskDao.delete(task)
}