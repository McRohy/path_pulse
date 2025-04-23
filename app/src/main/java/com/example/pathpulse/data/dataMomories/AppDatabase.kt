package com.example.pathpulse.data.dataMomories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/*
prepolulated database -> https://www.youtube.com/watch?v=pe28WeQ0VCc

 */
@Database(entities = [CountryEntity::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .createFromAsset("database/countries_v3.db") // Cesta k prepopulovanému súboru v assets
                    .fallbackToDestructiveMigration() // ak sa schéma zmení, databáza sa zničí a nahradí preload súborom
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}