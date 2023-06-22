package com.example.gruppenmeister.todos

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gruppenmeister.R
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

//Entity Klasse die gleichzeitig mit Room die Aufgabentabelle in der Datenbank anlegt
@Entity(tableName = "Aufgabe")
data class TaskItem(
    @ColumnInfo(name= "name")
    var taskName: String,
    @ColumnInfo(name="beschreibung")
    var taskDesc: String,
    @ColumnInfo(name ="fälligkeit")
    var taskDueString: String?,
    @ColumnInfo(name= "prio")
    var taskPrio: Int = 0,
    @ColumnInfo(name="abgeschlossen")
    var isCompleted: Boolean = false,
    @ColumnInfo(name="gruppenid")
    var gruppenid: Int = 0,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "aufgabenid")
    var aufgabenid: Int = 0,

    ) {

    //funtkionen zum setzen der Werte in der View
    fun taskDue():LocalDate? = if(taskDueString == null) null else LocalDate.parse(taskDueString, dateFormatter)
    fun checkImageRessource(): Int = if(isCompleted) R.drawable.baseline_check_box_24 else R.drawable.baseline_check_box_outline_blank_24
    fun checkImageColor(context: Context):Int = if (isCompleted) purple(context) else black(context)
    fun prioImageRessource(): Int = if(taskPrio == 1) R.drawable.baseline_star_24 else R.drawable.baseline_star_border_24
    fun prioImageColor(context: Context):Int = if (taskPrio == 1) gold(context) else white(context)

    private fun purple(context: Context) = ContextCompat.getColor(context, R.color.purple_200)

    private fun black(context: Context) = ContextCompat.getColor(context, R.color.black)

    private fun gold(context: Context) = ContextCompat.getColor(context, R.color.gold)
    private fun white(context: Context) = ContextCompat.getColor(context, R.color.white)

    companion object{
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE
    }
}
