package com.example.gruppenmeister.groups

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Entity Klasse die gleichzeitig mit Room die Gruppentabelle in der Datenbank anlegt
@Entity(tableName = "Gruppe")
data class GroupItem(
    @ColumnInfo(name= "name")
    var groupName: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "gruppenid")
    var gruppenid: Int = 0
) {

}
