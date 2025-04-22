package com.example.pathpulse.screens


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


data class AddUiState(
    val description: String = "",
)

class AddViewModel() : ViewModel() {

    val searchQuery = MutableStateFlow("")

    private val _uiState = MutableStateFlow(AddUiState())
    val uiState: StateFlow<AddUiState> = _uiState

    fun onSearchQueryChange(newQuery: String) {
        searchQuery.value = newQuery
    }

    fun onSearchQuerySubmit() {

    }

    fun onDescriptionChange(newDescription: String) {
        _uiState.value = _uiState.value.copy(description = newDescription)
    }

    fun save() {

    }
}
