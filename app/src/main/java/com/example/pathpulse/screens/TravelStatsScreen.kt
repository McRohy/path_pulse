package com.example.pathpulse.screens

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
import androidx.compose.foundation.layout.heightIn
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

@Composable
fun TravelStatsScreen(
    viewModel: TravelStatsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    // BoxWithConstraints nám dáva maxWidth a maxHeight priestoru
    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {

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
                    progress = uiState.progress,
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.small_spacer)))

            StatsCard(
                icon = Icons.Filled.TravelExplore,
                title = stringResource(R.string.visited_countries),
                value = uiState.numberOfCountriesVisited.toString()
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.small_spacer)))

            StatsCard(
                icon = Icons.Filled.LocationOn,
                title = stringResource(R.string.last_visited_country),
                value = uiState.lastUpdatedCountryName.toString()
            )
        }
    }
}

@Composable
fun MyCircularProgress(progress: Float, modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        progress = progress,
        color = MaterialTheme.colorScheme.primary,
        trackColor = Color.LightGray,
        strokeWidth = 20.dp,
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .aspectRatio(1f),
    )
}

@Composable
fun StatsCard(icon: ImageVector, title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp, max = 120.dp),
        shape = RoundedCornerShape(
            dimensionResource(R.dimen.card_rounded_shape_corner)
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
                    modifier = Modifier.fillMaxSize()
                        .padding(16.dp)
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

@Preview(showBackground = true)
@Composable
fun StatCardPreview() {
    PathPulseTheme{
        TravelStatsScreen()
    }
}