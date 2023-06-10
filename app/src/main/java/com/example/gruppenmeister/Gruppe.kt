package com.example.gruppenmeister

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Gruppe")
data class Gruppe(
    @PrimaryKey
    @ColumnInfo(name= "gruppenid")
    val gruppenid: Int,
    @ColumnInfo(name= "name")
    val name: String,
) {

}