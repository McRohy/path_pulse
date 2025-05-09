package com.example.pathpulse.screens.memories

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pathpulse.AppViewModelProvider
import com.example.pathpulse.R
import com.example.pathpulse.data.dataMomories.CountryEntity
import com.example.pathpulse.ui.theme.PathPulseTheme

@Composable
fun MemoriesScreen(
    viewModel: MemoriesViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateToMemory: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val countriesList by viewModel.countriesList.collectAsState()

    ContentForMemories(
        countriesList = countriesList,
        navigateToMemory = navigateToMemory,
        modifier = modifier
    )
}

@Composable
fun ContentForMemories(
    countriesList: List<CountryEntity>,
    navigateToMemory: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.small_padding)),
    ) {
        items(countriesList) { country ->

            Button(
                onClick = { navigateToMemory(country.id) },
                shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_shape_corner)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(dimensionResource(R.dimen.small_padding))
            )
            {
                Text(
                    text = country.name,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MemoriesPreview() {
    PathPulseTheme {
        ContentForMemories(
            countriesList = listOf(
                CountryEntity(id = 1, name = "Slovakia", description = "", updatedAt = 0L , rating = 0),
                CountryEntity(id = 2, name = "Hungary", description = "", updatedAt = 0L, rating = 0),
                CountryEntity(id = 3, name = "Austria", description = "", updatedAt = 0L, rating = 0)
            ),
            navigateToMemory = {},
         )
    }
}