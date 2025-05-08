package com.example.pathpulse.screens.memories

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestoreFromTrash
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pathpulse.AppViewModelProvider
import com.example.pathpulse.R
import com.example.pathpulse.ui.theme.PathPulseTheme
import kotlinx.coroutines.launch


@Composable
fun MemoryDetailScreen (
    navController: NavHostController,
    viewModel: MemoryDetailViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
    )
{
    val uiState = viewModel.uiState.collectAsState()

    MemoryDetail(
        name = uiState.value.countryDetails.name,
        description =  uiState.value.countryDetails.description,
        rating = uiState.value.countryDetails.rating,
        viewModel = viewModel,
        navController = navController,
        modifier = modifier
    )

}

@Composable
fun MemoryDetail(
    name: String,
    description: String,
    rating: Int,
    viewModel: MemoryDetailViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        shape = RoundedCornerShape(
            dimensionResource(R.dimen.rounded_shape_corner)
        ),
        border = BorderStroke(
            dimensionResource(R.dimen.border_stroke),
            MaterialTheme.colorScheme.primary
        ),
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.small_padding)),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,    //top a bottom zarovnanie
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)                               //zaberie všetok zostávajúci priestor
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.world_wallpaper),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = dimensionResource(R.dimen.rounded_shape_corner),
                                topEnd = dimensionResource(R.dimen.rounded_shape_corner)
                            )
                        )
                )
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(dimensionResource(R.dimen.medium_padding))
                )
                Rating(
                    rating = rating,
                    modifier = Modifier.padding(dimensionResource(R.dimen.small_padding))
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    textAlign = TextAlign.Justify
                )
            }

            // tlačidlo vždy na spodku Column
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        viewModel.clearMemory()
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.medium_padding))
                    .background(
                        color = Color.Gray,
                        shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_shape_corner))
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.RestoreFromTrash,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.small_padding))
                )
            }
        }
    }
}

@Composable
fun Rating(
    modifier: Modifier = Modifier,
    rating: Int = 0,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {

        for (index in 1..10) {
            Icon(
                imageVector =
                if (index <= rating) {
                    Icons.Rounded.Star
                } else {
                    Icons.Rounded.StarOutline
                },
                contentDescription = null,
                modifier = Modifier
                    .size(35.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun OneDetailMemoriesPreview() {
    PathPulseTheme {
        MemoryDetail(
            name = "Slovensko",
            rating = 2,
            description = "Krásna krajina s bohatou históriou a nádhernou prírodou.Krásna krajina s bohatou históriou a nádhernou prírodou.",
            viewModel = viewModel(factory = AppViewModelProvider.Factory),
            navController = TODO(),
        )
    }
}