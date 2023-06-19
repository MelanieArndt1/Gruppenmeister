package com.example.gruppenmeister.todos

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val TaskDao: TaskDao)
{
    val allTaskItems: Flow<List<TaskItem>> = TaskDao.allTaskItems()

    @WorkerThread
    suspend fun insertTaskItem(TaskItem: TaskItem) {
        TaskDao.insertTaskItem(TaskItem)
    }

    @WorkerThread
    suspend fun updateTaskItem(TaskItem: TaskItem) {
        TaskDao.updateTaskItem(TaskItem)
    }

    @WorkerThread
    suspend fun deleteTaskItem(TaskItem: TaskItem) {
        TaskDao.deleteTaskItem(TaskItem)
    }
}