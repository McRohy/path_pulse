package com.example.pathpulse.data.dataMomories

import kotlinx.coroutines.flow.Flow

/**
 * Lokálna implementácia repozitára krajín.
 *
 * Slúži na prístup k databáze krajín cez CountryDao a poskytuje
 * všetky operácie definované v rozhraní CountriesRepository.
 */
class LocalCountriesRepository(private val countryDao: CountryDao) : CountriesRepository {

    /**
     * Načíta všetky krajiny z lokálnej databázy.
     */
    override fun readAll() =
        countryDao.readAll()

    /**
     * Vyhľadá krajiny podľa zadaného dotazu (napr. názvu).
     */
    override fun searchCountries(query: String) =
        countryDao.searchCountries(query)

    /**
     * Aktualizuje data o krajine.
     */
    override suspend fun updateDescriptionByName(country: CountryEntity) {
        countryDao.updateDescriptionByName(
            name = country.name,
            description = country.description,
            updatedAt = System.currentTimeMillis() / 1000, //sekundova presnost
            rating = country.rating,
            imgUri = country.imgUri
        )
    }


    /**
     * Načíta všetky krajiny, ktoré majú vyplnenie.
     */
    override fun getDescribedCountries(): Flow<List<CountryEntity>> =
        countryDao.getCountriesWithDescription()

    /**
     * Načíta názov krajiny, ktorá bola naposledy aktualizovaná.
     */
    override fun getLastUpdatedCountryName(): Flow<String?> =
        countryDao.getLastUpdatedCountryName()

    /**
     * Zistí počet spomienkových krajín.
     */
    override fun getCountriesCount(): Flow<Int> =
        countryDao.getCountriesCount()

    /**
     * Vymaže v pamäti uložené údaje pre krajinu so zadaným názvom.
     */
    override suspend fun clearMemory(name: String) =
        countryDao.clearMemory(name)

    /**
     * Načíta entitu krajiny podľa jej ID.
     */
    override fun getCountryById(id: Int): Flow<CountryEntity> =
        countryDao.getCountryById(id)
}