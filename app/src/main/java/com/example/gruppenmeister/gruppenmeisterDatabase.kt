package com.example.gruppenmeister

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(
    entities = [Gruppe::class, Aufgabe::class],
//    autoMigrations = [
//        AutoMigration (from = 1, to = 2)
//    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(AufgabenConverters::class)
abstract class gruppenmeisterDatabase : RoomDatabase() {

    abstract fun AufgabeDao(): AufgabeDao
    abstract fun GruppenDao():GruppenDao

    companion object {
        @Volatile
        private var INSTANCE: gruppenmeisterDatabase? = null

        fun getDatabase(context: Context): gruppenmeisterDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                synchronized(this) {
                    // Pass the database to the INSTANCE
                    INSTANCE = buildDatabase(context)
                }
            }
            // Return database.
            return INSTANCE!!
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // The following query will add a new column called lastUpdate to the notes database
                database.execSQL("ALTER TABLE Aufgabe ADD COLUMN lastUpdate INTEGER NOT NULL DEFAULT 0")
            }
        }

        private fun buildDatabase(context: Context): gruppenmeisterDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                gruppenmeisterDatabase::class.java,
                "notes_database"
            )
                //.addMigrations(MIGRATION_1_2)
                .build()
        }
    }
}