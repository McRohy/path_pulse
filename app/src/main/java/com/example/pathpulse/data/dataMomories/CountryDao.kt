package com.example.pathpulse.data.dataMomories

import androidx.room.Dao
import androidx.room.Query

import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {
    @Query("SELECT * FROM country")
    fun readAll(): Flow<List<CountryEntity>>

    @Query("SELECT * FROM country WHERE name LIKE '%' || :query || '%' AND updatedAt IS NULL")
    fun searchCountries(query: String): Flow<List<CountryEntity>>

    @Query("UPDATE country SET description = :description, updatedAt = :updatedAt, rating = :rating WHERE name = :name")
    suspend fun updateDescriptionByName(
        name: String,
        description: String?,
        updatedAt: Long,
        rating: Int?
    )

    @Query("SELECT * FROM country WHERE description IS NOT NULL")
    fun getCountriesWithDescription(): Flow<List<CountryEntity>>

    @Query("SELECT name FROM country WHERE updatedAt IS NOT NULL ORDER BY updatedAt DESC LIMIT 1")
    fun getLastUpdatedCountryName(): Flow<String?>

    @Query("SELECT COUNT(*) FROM country WHERE updatedAt IS NOT NULL")
    fun getCountriesCount(): Flow<Int>

    @Query("UPDATE country SET description = null, updatedAt = null, rating = null WHERE name = :name")
    suspend fun clearMemory(name: String)

    @Query("SELECT * from country WHERE id = :id")
    fun getCountryById(id: Int): Flow<CountryEntity>

}