package com.example.gruppenmeister.todos

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gruppenmeister.R
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

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

    fun taskDue():LocalDate? = if(taskDueString == null) null else LocalDate.parse(taskDueString, dateFormatter)
    fun checkImageRessource(): Int = if(isCompleted) R.drawable.baseline_check_box_24 else R.drawable.baseline_check_box_outline_blank_24
    fun prioImageRessource(): Int = if(taskPrio == 1) R.drawable.baseline_star_24 else R.drawable.baseline_star_border_24
    fun imageColor(context: Context):Int = if (isCompleted) purple(context) else black(context)
    private fun purple(context: Context) = ContextCompat.getColor(context, R.color.purple_200)

    private fun black(context: Context) = ContextCompat.getColor(context, R.color.black)

    companion object{
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE
    }
}