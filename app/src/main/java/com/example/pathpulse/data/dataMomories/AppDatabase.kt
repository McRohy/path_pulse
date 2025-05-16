package com.example.pathpulse.data.dataMomories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Hlavná databáza aplikácie PathPulse.
 * Načíta predinicializovaný súbor z assets.
 *
 * preloaded database -> https://www.youtube.com/watch?v=pe28WeQ0VCc
 */
@Database(entities = [CountryEntity::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Poskytuje DAO na prístup k tabuľke krajín.
     */
    abstract fun countryDao(): CountryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Vracia singleton inštanciu databázy.
         * Pri prvom vytvorení načíta predinicializovanú databázu z assets.
         * @param context Aplikačný kontext.
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .createFromAsset("database/countries_v5.db")
                    //.fallbackToDestructiveMigration() // ak sa schéma zmení, databáza sa zničí a nahradí preload súborom
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}