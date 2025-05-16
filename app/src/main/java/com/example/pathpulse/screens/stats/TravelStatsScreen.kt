package com.example.pathpulse.screens.stats

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pathpulse.AppViewModelProvider
import com.example.pathpulse.R
import com.example.pathpulse.ui.theme.PathPulseTheme

/**
 * Obrazovka štatistík cestovania.
 * Získa stav z TravelStatsViewModel a odovzdá ho do ContentForTravelStats.
 *
 * @param modifier Modifier pre vonkajšie prispôsobenie.
 * @param viewModel Inštancia ViewModelu so štatistikami (Factory).
 */
@Composable
fun TravelStatsScreen(
    modifier: Modifier = Modifier,
    viewModel: TravelStatsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    ContentForTravelStats(
        valueCountries = uiState.numberOfCountriesVisited.toString(),
        valueLastCountry = uiState.lastUpdatedCountryName.toString(),
        progress = uiState.progress,
        modifier = modifier
    )
}

/**
 * Vnútorný layout obrazovky štatistík, ktorý zobrazuje:
 * kruhový progress indikátor so zadaným progressom
 * štatistické karty – počet navštívených krajín a názov poslednej krajiny.
 *
 * @param valueCountries  Reťazec s počtom krajín.
 * @param valueLastCountry Reťazec s názvom poslednej krajiny.
 * @param progress  Hodnota 0f..1f pre kruhový progress.
 * @param modifier Modifier pre vonkajšie prispôsobenie.
 */
@Composable
fun ContentForTravelStats(
    valueCountries: String,
    valueLastCountry: String,
    progress: Float,
    modifier: Modifier = Modifier
) {
    // BoxWithConstraints poskytuje maxWidth a maxHeight priestoru
    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        // Určenie výšky pre CircularProgress ako 60% z dostupnej výšky
        val circleCardHeight = maxHeight * 0.6f

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(dimensionResource(R.dimen.small_padding))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(circleCardHeight),
                contentAlignment = Alignment.Center
            ) {
                MyCircularProgress(
                    progress = progress,
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.small_spacer)))

            StatsCard(
                icon = Icons.Filled.TravelExplore,
                title = stringResource(R.string.visited_countries),
                value = valueCountries
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.small_spacer)))

            StatsCard(
                icon = Icons.Filled.LocationOn,
                title = stringResource(R.string.last_visited_country),
                value = valueLastCountry
            )
        }
    }
}

/**
 * Kruhový progress indikátor.
 *
 * @param progress Hodnota 0f..1f určujúca, ako plný bude kruh.
 * @param modifier Modifier pre vonkajšie prispôsobenie.
 */
@Composable
fun MyCircularProgress(
    progress: Float,
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        progress = { progress },
        modifier = modifier
            .fillMaxHeight(0.9f)
            .aspectRatio(1f),
        color = MaterialTheme.colorScheme.primary,
        strokeWidth = 20.dp,
        trackColor = Color.LightGray,
    )
}

/**
 * Štatistická karta s ikonou, popisom a hodnotou.
 *
 * @param icon Ikona z Material Icons.
 * @param title Popis štatistiky.
 * @param value Hodnota štatistiky .
 * @param modifier Modifier pre vonkajšie prispôsobenie.
 */
@Composable
fun StatsCard(
    icon: ImageVector,
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(
            dimensionResource(R.dimen.rounded_shape_corner)
        ),
        border = BorderStroke(
            dimensionResource(R.dimen.border_stroke),
            MaterialTheme.colorScheme.primary
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(dimensionResource(R.dimen.small_padding))
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.medium_padding))
                )
            }

            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = value,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

/**
 * Náhľad pre ContentForTravelStats s ukážkovými dátami.
 */
@Preview(showBackground = true)
@Composable
fun StatCardPreview() {
    PathPulseTheme {
        ContentForTravelStats(
            valueCountries = "50",
            valueLastCountry = "Slovakia",
            progress = 0.3F,
        )
    }
}