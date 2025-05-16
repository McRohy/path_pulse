package com.example.pathpulse.data.dataMomories


import kotlinx.coroutines.flow.Flow

/**
 * Rozhranie pre prácu s dátami krajín.
 */
interface CountriesRepository {
    /**
     * Načíta všetky krajiny.
     * @return Flow so zoznamom všetkých entít CountryEntity.
     */
    fun readAll(): Flow<List<CountryEntity>>

    /**
     * Vyhľadá krajiny podľa textu v názve.
     * @param query Hľadaný reťazec v názve krajiny.
     * @return Flow so zoznamom krajín, ktorých názvy obsahujú query.
     */
    fun searchCountries(query: String): Flow<List<CountryEntity>>

    /**
     * Aktualizuje popis krajiny podľa jej názvu.
     * @param country Entita krajiny obsahujúca názov, nový popis a hodnotenie.
     */
    suspend fun updateDescriptionByName(country: CountryEntity)

    /**
     * Získa všetky krajiny, ktoré už majú vyplnený popis.
     * @return Flow so zoznamom entít CountryEntity s popisom.
     */
    fun getDescribedCountries(): Flow<List<CountryEntity>>

    /**
     * Zistí názov krajiny, ktorá bola naposledy aktualizovaná.
     * @return Flow s názvom krajiny alebo null, ak žiadna nebola upravená.
     */
    fun getLastUpdatedCountryName(): Flow<String?>

    /**
     * Spočíta celkový počet krajín.
     * @return Flow s počtom krajín.
     */
    fun getCountriesCount(): Flow<Int>

    /**
     * Vymaže dáta krajiny podľa názvu.
     * @param name Názov krajiny, ktorej dáta sa majú vymazať.
     */
    suspend fun clearMemory(name: String)

    /**
     * Načíta krajinu podľa jej identifikátora.
     * @param id Identifikátor krajiny.
     * @return Flow s entitou CountryEntity danej krajiny.
     */
    fun getCountryById(id: Int): Flow<CountryEntity>
}