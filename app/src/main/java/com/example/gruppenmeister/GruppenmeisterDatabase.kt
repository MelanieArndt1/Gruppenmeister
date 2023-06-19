package com.example.gruppenmeister

import android.content.Context
import androidx.room.*
import com.example.gruppenmeister.groups.GroupItem
import com.example.gruppenmeister.groups.GroupDao
import com.example.gruppenmeister.todos.TaskDao
import com.example.gruppenmeister.todos.TaskItem


@Database(
    entities = [GroupItem::class, TaskItem::class],
    version = 1,
    exportSchema = false
)

abstract class GruppenmeisterDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun groupDao(): GroupDao

    companion object {
        @Volatile
        private var INSTANCE: GruppenmeisterDatabase? = null

        fun getDatabase(context: Context): GruppenmeisterDatabase {
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GruppenmeisterDatabase::class.java,
                    "groupmaster2_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}