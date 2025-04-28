package com.example.pathpulse.data.dataMomories


import kotlinx.coroutines.flow.Flow

interface CountriesRepository {
    fun readAll(): Flow<List<CountryEntity>>
    fun searchCountries(query: String): Flow<List<CountryEntity>>
    suspend fun updateDescriptionByName(country: CountryEntity)

    fun getDescribedCountries(): Flow<List<CountryEntity>>
    fun getLastUpdatedCountryName(): Flow<String?>
    fun getCountriesCount(): Flow<Int>
}