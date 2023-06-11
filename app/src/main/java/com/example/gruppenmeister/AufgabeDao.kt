package com.example.gruppenmeister

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AufgabeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAufgabe(aufgabe: Aufgabe)

    @Query("SELECT * FROM Aufgabe ORDER BY aufgabenid DESC")
    fun getAufgaben(): Flow<List<Aufgabe>>

    @Query("SELECT * FROM Aufgabe ORDER BY aufgabenid LIMIT 1")
    fun getAufgabe():Aufgabe

    @Query("INSERT INTO Aufgabe(aufgabenid, name, beginnt, endet, beschreibung, prio, gruppenid) VALUES (1, 'test','2023-01-01', '2023-01-10', 'test', 1, 1)  ")
    fun testdaten()
    @Update
    suspend fun updateAufgabe(aufgabe: Aufgabe)

    @Delete
    suspend fun deleteAufgabe(aufgabe: Aufgabe)
}