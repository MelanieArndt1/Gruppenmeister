package com.example.gruppenmeister

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.gruppenmeister.groups.GroupItem
import com.example.gruppenmeister.groups.GroupDao


@Database(
    entities = [GroupItem::class, Aufgabe::class],
//    autoMigrations = [
//        AutoMigration (from = 1, to = 2)
//    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(AufgabenConverters::class)
abstract class gruppenmeisterDatabase : RoomDatabase() {

    abstract fun AufgabeDao(): AufgabeDao
    abstract fun GruppenDao(): GroupDao

    companion object {
        @Volatile
        private var INSTANCE: gruppenmeisterDatabase? = null

        fun getDatabase(context: Context): gruppenmeisterDatabase {
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    gruppenmeisterDatabase::class.java,
                    "groupmaster_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // The following query will add a new column called lastUpdate to the notes database
                database.execSQL("ALTER TABLE Aufgabe ADD COLUMN lastUpdate INTEGER NOT NULL DEFAULT 0")
            }
        }
    }
}