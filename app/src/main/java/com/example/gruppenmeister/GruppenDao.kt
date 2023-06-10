package com.example.gruppenmeister

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GruppenDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGruppe(gruppe: Gruppe)

    @Query("SELECT * FROM Gruppe ORDER BY gruppenid DESC")
    fun getGruppen(): Flow<List<Gruppe>>

    @Query("SELECT * FROM Gruppe ORDER BY gruppenid LIMIT 1")
    fun getGruppe():Gruppe

    @Update
    suspend fun updateGruppe(gruppe: Gruppe)

    @Delete
    suspend fun deleteGruppe(gruppe: Gruppe)
}