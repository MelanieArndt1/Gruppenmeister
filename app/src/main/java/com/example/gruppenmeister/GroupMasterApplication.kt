package com.example.gruppenmeister

import android.app.Application
import com.example.gruppenmeister.groups.GroupRepository
import com.example.gruppenmeister.todos.TaskRepository

//Klasse zum initalisieren der Datenbank und der Repositorys
class GroupMasterApplication: Application()
{
    private val database by lazy { GruppenmeisterDatabase.getDatabase(this)}
    val groupRepository by lazy { GroupRepository(database.groupDao()) }
    val taskRepository by lazy { TaskRepository(database.taskDao()) }
}
