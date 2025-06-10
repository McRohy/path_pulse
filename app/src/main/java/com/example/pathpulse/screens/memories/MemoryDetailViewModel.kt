package com.example.pathpulse.screens.memories

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pathpulse.constans.TIMEOUT_MILLIS
import com.example.pathpulse.data.dataMomories.CountriesRepository
import com.example.pathpulse.helpers.CountryMappers.toDetails
import com.example.pathpulse.navigation.OneMemoryDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel pre detail spomienky (krajiny), ktorý načítava údaje z CountriesRepository
 * na základe ID získaného zo SavedStateHandle.
 *
 * @param savedStateHandle Umožňuje čítať navigačné argumenty.
 * @param countriesRepository Repozitár, z ktorého sa získavajú dáta o krajine.
 */
class MemoryDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val countriesRepository: CountriesRepository
) : ViewModel() {

    // prečítame si ID a name priamo zo SavedStateHandle
    private val memoryId: Int = checkNotNull(
        savedStateHandle[OneMemoryDestination.memoryIdArg]
    )

    /**
     * Reaktívny tok, ktorý poskytuje detailné informácie o vybranej krajine.
     *
     * StateFlow načíta dáta z countriesRepository podľa memoryId.
     * Odfiltrovanie prípadných null-hodnôt, aby sa do UI nedostával nesprávny stav.
     * Mapovanie entity krajiny na MemoryDetailsUiState.
     *
     */
    val uiState: StateFlow<MemoryDetailsUiState> =
        countriesRepository.getCountryById(memoryId)
            .filterNotNull()
            .map {
                MemoryDetailsUiState(countryDetails = it.toDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = MemoryDetailsUiState()
            )

    /**
     * Odstráni (vyčistí) spomienku v repozitári podľa názvu krajiny v uiState.
     */
    suspend fun clearMemory(){
        countriesRepository.clearMemory(uiState.value.countryDetails.name)
    }
}

/**
 * Datová trieda reprezentujúca UI stav detailu krajiny.
 *
 * @property countryDetails Podrobnosti o krajine.
 */
data class MemoryDetailsUiState(
    val countryDetails: CountryDetails = CountryDetails()
)