package com.example.gruppenmeister.groups

import androidx.room.*
import kotlinx.coroutines.flow.Flow

//Dao Interface von Room für die CRUD Schicht
@Dao
interface GroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroupItem(groupItem: GroupItem)

    @Query("SELECT * FROM Gruppe ORDER BY gruppenid DESC")
    fun allGroupItems(): Flow<List<GroupItem>>

    @Query("SELECT * FROM Gruppe ORDER BY gruppenid LIMIT 1")
    fun getGroup(): GroupItem

    @Update
    suspend fun updateGroupItem(groupItem: GroupItem)

    @Delete
    suspend fun deleteGroup(groupItem: GroupItem)
}
