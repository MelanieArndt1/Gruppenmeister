package com.example.gruppenmeister.todos

interface TaskItemClickListener {
    fun showTaskItemDetails (taskItem: TaskItem)
    fun completeTaskItem(taskItem: TaskItem)

}