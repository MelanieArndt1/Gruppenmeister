package com.example.gruppenmeister

import android.app.Application
import com.example.gruppenmeister.groups.GroupRepository

class GroupMasterApplication: Application()
{
    private val database by lazy { gruppenmeisterDatabase.getDatabase(this)}
    val repository by lazy { GroupRepository(database.GruppenDao()) }
}