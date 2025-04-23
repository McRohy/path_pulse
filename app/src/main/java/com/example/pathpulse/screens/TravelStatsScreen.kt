package com.example.pathpulse.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pathpulse.AppViewModelProvider
import com.example.pathpulse.ui.theme.PathPulseTheme

@Composable
fun TravelStatsScreen(
    viewModel: TravelStatsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
        ) {

            MyCircularProgress(progress = 0.12f)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .aspectRatio(1f)
                ) {
                    Icon(
                        imageVector = Icons.Filled.TravelExplore,
                        contentDescription = "Ikona cestovania",
                        modifier = Modifier
                            .fillMaxSize()
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
                    Text(text = "Visited Countries", textAlign = TextAlign.Center)
                    Text(text = "XXXX", textAlign = TextAlign.Center)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .aspectRatio(1f)
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "Ikona lokácie",
                        modifier = Modifier
                            .fillMaxSize()
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
                    Text(text = "Last visited country", textAlign = TextAlign.Center)
                    Text(text = "XXXX", textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Composable
fun MyCircularProgress(
    progress: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Šedé pozadie
        CircularProgressIndicator(
            progress = 1f,
            color = Color.LightGray,
            strokeWidth = 40.dp,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .padding(50.dp),
        )
        // Skutočná „vyplnená“ časť
        CircularProgressIndicator(
            progress = progress,
            color = Color.Blue,
            strokeWidth = 40.dp,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .padding(50.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StatCardPreview() {
    PathPulseTheme{
        TravelStatsScreen()
    }
}