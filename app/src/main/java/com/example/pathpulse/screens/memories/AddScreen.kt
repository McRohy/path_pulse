package com.example.pathpulse.screens.memories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pathpulse.AppViewModelProvider
import com.example.pathpulse.ui.theme.PathPulseTheme
import kotlinx.coroutines.launch


@Composable
fun AddScreen(
    viewModel: AddViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    if (uiState.searchActive) {
        Box(modifier = modifier.fillMaxSize())
        {
            AddSearchBar(query = uiState.searchQuery, viewModel = viewModel)
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {

            Box(modifier = modifier.fillMaxSize())
            {
                AddSearchBar(query = uiState.searchQuery, viewModel = viewModel)
            }

            Spacer(Modifier.height(20.dp))

            Text(text = "Describe your memory")
            OutlinedTextField(
                value = uiState.countryDetails.description.orEmpty(),
                onValueChange = viewModel::onDescriptionChange,
                label = { Text("My journey to...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 150.dp, max = 250.dp),
                maxLines = 10,
                shape = RoundedCornerShape(16.dp),
            )

            Spacer(Modifier.height(20.dp))

            Text(text = "Load one your favourite picture")
            Button(
                onClick = {
                    // TODO: Implementácia načítania obrázku
                },
                shape = RoundedCornerShape(3.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("+")
            }

            Spacer(Modifier.height(20.dp))

            Button(
                enabled = uiState.isEntryValid,
                onClick = {
                    coroutineScope.launch {
                        viewModel.save()
                        navController.popBackStack()
                    }
                },
                shape = RoundedCornerShape(16.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp, max = 200.dp)
            ) {
                Text("ADD IN COLLECTION")
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSearchBar(
    query: String,
    viewModel: AddViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    SearchBar(
        query = query,
        onQueryChange = { newQuery ->
            viewModel.onSearchQueryChange(newQuery)
        },
        onSearch = {
            viewModel.onSearchQuerySubmit()
        },
        active = uiState.searchActive,
        onActiveChange = { isActive ->
            viewModel.onSearchBarActiveChange(isActive)
        },
        placeholder = { Text("Search country") },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyColumn() {
            items(uiState.searchResults) { country ->
                Text(
                    text = country.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onSearchQueryChange(country.name)
                            viewModel.onCountrySelected(country)
                            viewModel.onSearchBarActiveChange(false)
                        }
                        .padding(8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddScreenPreview() {
    PathPulseTheme  {
        //AddScreen()
    }
}
