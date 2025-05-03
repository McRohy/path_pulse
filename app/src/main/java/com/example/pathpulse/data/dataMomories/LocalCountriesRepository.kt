package com.example.pathpulse.data.dataMomories

import kotlinx.coroutines.flow.Flow

class LocalCountriesRepository(private val countryDao: CountryDao) : CountriesRepository {
    override fun readAll() = countryDao.readAll()
    override fun searchCountries(query: String) = countryDao.searchCountries(query)

    override suspend fun updateDescriptionByName(country: CountryEntity) {
        countryDao.updateDescriptionByName(
            name = country.name,
            description = country.description,
            updatedAt = System.currentTimeMillis() / 1000, //sekundova presnost
            rating = country.rating
            )
    }

    override fun getDescribedCountries(): Flow<List<CountryEntity>> =
        countryDao.getCountriesWithDescription()

    override fun getLastUpdatedCountryName(): Flow<String?> =
        countryDao.getLastUpdatedCountryName()

    override fun getCountriesCount(): Flow<Int> =
        countryDao.getCountriesCount()

    override suspend fun clearMemory(name: String) =
        countryDao.clearMemory(name)

    override fun getCountryById(id: Int): Flow<CountryEntity> =
        countryDao.getCountryById(id)
}