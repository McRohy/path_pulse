package com.example.pathpulse.data.dataMomories

class OfflineCountriesRepository(
    private val countryDao: CountryDao
) : CountriesRepository {
    override fun readAll() = countryDao.readAll()
    override fun searchCountries(query: String) = countryDao.searchCountries(query)

    override suspend fun updateDescriptionByName(country: CountryEntity) {
        countryDao.updateDescriptionByName(
            name = country.name,
            description = country.description
        )
    }
}