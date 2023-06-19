package com.example.gruppenmeister.todos

interface TaskItemClickListener {
    fun editTaskItem (taskItem: TaskItem)
    fun completeTaskItem(taskItem: TaskItem)
}