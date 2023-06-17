package com.example.gruppenmeister.groups

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class GroupRepository(private val groupDao: GroupDao)
{
    val allGroupItems: Flow<List<GroupItem>> = groupDao.allGroupItems()

    @WorkerThread
    suspend fun insertGroupItem(groupItem: GroupItem) {
        groupDao.insertGroupItem(groupItem)
    }

    @WorkerThread
    suspend fun updateGroupItem(groupItem: GroupItem) {
        groupDao.updateGroupItem(groupItem)
    }
}