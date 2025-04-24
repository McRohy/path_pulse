package com.example.pathpulse.screens.memories

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pathpulse.AppViewModelProvider
import com.example.pathpulse.data.dataMomories.CountryEntity
import com.example.pathpulse.ui.theme.PathPulseTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MemoriesScreen(
    viewModel: MemoriesViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = modifier.fillMaxSize())
    {
        if (uiState.selectedCountry == null) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(6.dp),
            ) {
                items(uiState.countriesList) { country ->
                    CountryCard(
                        country = country,
                        onClick = { viewModel.selectCountry(country)}
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()

            ) {
                DetailCard(
                    country = uiState.selectedCountry!!,
                    onClose = { viewModel.selectCountry(null) }
                )


            }

        }


    }
}


@Composable
fun CountryCard(
    country: CountryEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable(onClick = onClick)
            .padding( 6.dp)
            .border(
                6.dp,
                MaterialTheme.colorScheme.primary,
                RoundedCornerShape(16.dp)
            ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = country.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = modifier.padding(12.dp))
        }
    }
}

@Composable
fun DetailCard(
    country: CountryEntity,
    onClose: () -> Unit,
    modifier: Modifier = Modifier )
{
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding( 6.dp)
            .border(
                6.dp,
                MaterialTheme.colorScheme.primary,
                RoundedCornerShape(16.dp)
            ),
    )
    {
        val scrollState = rememberScrollState()

        Column (modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween)
            {
                Text(text = country.name,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(6.dp)
                )

                Button(
                    onClick = onClose,
                    shape = RoundedCornerShape(16.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )

                )
                {
                    Icon(
                        imageVector   = Icons.Filled.Close,
                        contentDescription = "Close"
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = country.description!!,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(6.dp)
            )

        }


    }


}



@Preview(showBackground = true)
@Composable
fun MemoriesPreview() {
    val country = CountryEntity(id = 1, name = "Slovensko", description = "Krásna krajina pod Tatrami", 0)
    PathPulseTheme {
        DetailCard(country = country, onClose = {})
    }
}