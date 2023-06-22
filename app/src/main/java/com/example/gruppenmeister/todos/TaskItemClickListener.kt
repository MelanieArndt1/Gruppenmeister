package com.example.gruppenmeister.todos

//Interface, für die ClickListener Funktionalitäten
interface TaskItemClickListener {
    fun showTaskItemDetails (taskItem: TaskItem)
    fun completeTaskItem(taskItem: TaskItem)

}