package com.example.pathpulse.screens.memories

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pathpulse.data.dataMomories.CountriesRepository
import com.example.pathpulse.navigation.OneMemoryDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MemoryDetailViewModel(
    val savedStateHandle: SavedStateHandle,
    private val countriesRepository: CountriesRepository
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }


    // prečítame si ID a name priamo zo SavedStateHandle
    private val memoryId: Int = checkNotNull(
        savedStateHandle[OneMemoryDestination.memoryIdArg]
    )


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

    suspend fun clearMemory(){
        countriesRepository.clearMemory(uiState.value.countryDetails.name)
    }
}

data class MemoryDetailsUiState(
    val countryDetails: CountryDetails = CountryDetails()
)