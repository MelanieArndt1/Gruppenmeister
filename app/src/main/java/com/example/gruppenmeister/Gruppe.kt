package com.example.gruppenmeister

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity(tableName = "Gruppe")
data class Gruppe(
    @ColumnInfo(name= "name")
    var groupName: String,
    @PrimaryKey
    @ColumnInfo(name= "gruppenid")
    var gruppenid: UUID = UUID.randomUUID(),
) {

}