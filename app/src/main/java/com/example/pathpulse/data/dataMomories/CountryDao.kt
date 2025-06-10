package com.example.pathpulse.data.dataMomories

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update

import kotlinx.coroutines.flow.Flow

/**
 * DAO pre tabuľku krajín v lokálnej databáze.
 */
@Dao
interface CountryDao {
    /**
     * Načíta všetky záznamy krajín.
     * @return Flow so zoznamom CountryEntity.
     */
    @Query("SELECT * FROM country")
    fun readAll(): Flow<List<CountryEntity>>

    /**
     * Vyhľadá krajiny podľa časti názvu, ktoré nemajú popis.
     * @param query Podreťazec, ktorý sa hľadá v názve krajiny.
     * @return Flow so zoznamom CountryEntity.
     */
    @Query("SELECT * FROM country WHERE name LIKE '%' || :query || '%' AND updatedAt IS NULL")
    fun searchCountries(query: String): Flow<List<CountryEntity>>

    /**
     * Aktualizuje data o krajine.
     *
     */
    @Query("UPDATE country SET description = :description, updatedAt = :updatedAt, rating = :rating, imgUri = :imgUri WHERE name = :name")
    suspend fun updateDescriptionByName(
        name: String,
        description: String?,
        updatedAt: Long,
        rating: Int?,
        imgUri: String?
    )


    /**
     * Získa krajiny, ktoré majú vyplnený popis.
     * @return Flow so zoznamom CountryEntity s popisom.
     */
    @Query("SELECT * FROM country WHERE description IS NOT NULL")
    fun getCountriesWithDescription(): Flow<List<CountryEntity>>

    /**
     * Zistí názov krajiny, ktorá bola naposledy aktualizovaná.
     * @return Flow s názvom krajiny alebo null.
     */
    @Query("SELECT name FROM country WHERE updatedAt IS NOT NULL ORDER BY updatedAt DESC LIMIT 1")
    fun getLastUpdatedCountryName(): Flow<String?>

    /**
     * Zistí počet krajín s vyplneným popisom.
     * @return Flow s počtom krajín.
     */
    @Query("SELECT COUNT(*) FROM country WHERE updatedAt IS NOT NULL")
    fun getCountriesCount(): Flow<Int>

    /**
     * Vymaže popis, čas a hodnotenie krajiny podľa názvu.
     * @param name Názov krajiny.
     */
    @Query("UPDATE country SET description = null, updatedAt = null, rating = null WHERE name = :name")
    suspend fun clearMemory(name: String)

    /**
     * Načíta krajinu podľa jej ID.
     * @param id Identifikátor krajiny.
     * @return Flow so záznamom CountryEntity.
     */
    @Query("SELECT * from country WHERE id = :id")
    fun getCountryById(id: Int): Flow<CountryEntity>

}