package com.example.gruppenmeister

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Aufgabe")
data class Aufgabe(
    @PrimaryKey
    @ColumnInfo(name= "aufgabenid")
    val aufgabenid: Int,
    @ColumnInfo(name= "name")
    val name: String,
    @ColumnInfo(name ="beginnt")
    val beginnt: Date,
    @ColumnInfo(name ="endet")
    val endet: Date,
    @ColumnInfo(name="beschreibung")
    val beschreibung: String,
    @ColumnInfo(name= "prio")
    val prio: Int,
    @ColumnInfo(name="gruppenid")
    val gruppenid: Int

) {

}