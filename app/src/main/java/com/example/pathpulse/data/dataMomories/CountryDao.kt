package com.example.pathpulse.data.dataMomories

import androidx.room.Dao
import androidx.room.Query

import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {
    @Query("SELECT * FROM country")
    fun readAll(): Flow<List<CountryEntity>>

    @Query("SELECT * FROM country WHERE name LIKE '%' || :query || '%'")
    fun searchCountries(query: String): Flow<List<CountryEntity>>

    @Query("UPDATE country SET description = :description WHERE name = :name")
    suspend fun updateDescriptionByName(
        name: String,
        description: String?
    )
}