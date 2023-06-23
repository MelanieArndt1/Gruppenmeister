package com.example.gruppenmeister.todos

import androidx.room.*
import kotlinx.coroutines.flow.Flow
//Dao Interface von Room für die CRUD-Schicht
@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTaskItem(taskItem: TaskItem)

    @Query("SELECT * FROM Aufgabe ORDER BY aufgabenid DESC")
    fun allTaskItems(): Flow<List<TaskItem>>

    @Query("SELECT * FROM Aufgabe ORDER BY aufgabenid LIMIT 1")
    fun getTaskItem(): TaskItem

    @Update
    suspend fun updateTaskItem(taskItem: TaskItem)

    @Delete
    suspend fun deleteTaskItem(taskItem: TaskItem)
}
