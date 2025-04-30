package com.example.pathpulse.screens.explorer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pathpulse.AppViewModelProvider
import com.example.pathpulse.R
import com.example.pathpulse.data.dataExplorer.CountryExplorer
import com.example.pathpulse.data.dataExplorer.DataSource
import com.example.pathpulse.ui.theme.PathPulseTheme

@Composable
fun ExplorerScreen(
    viewModel: ExplorerViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(DataSource.countries) {
            ExplorerCard(
                country = it,
                modifier = Modifier.padding(dimensionResource(R.dimen.small_padding)),
            )
        }
    }
}

@Composable
fun ExplorerCard(country: CountryExplorer, modifier: Modifier = Modifier) {
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
    ) {
        var expanded by rememberSaveable { mutableStateOf(false) }

        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Image(
                    painter = painterResource(country.imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            RoundedCornerShape(
                                topStart = dimensionResource(R.dimen.rounded_shape_corner),
                                topEnd = dimensionResource(R.dimen.rounded_shape_corner)
                            )
                        )
                        .blur(
                            radiusX = 5.dp,
                            radiusY = 5.dp,
                            edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(1.dp))
                        )
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.small_medium_padding))
                ) {
                    Text(
                        text = stringResource(id = country.titleRes),
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    IconButton(
                        onClick = { expanded = !expanded },
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(36.dp) // velikost ikony
                        )
                    }
                }
            }
        }
        if (expanded) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = dimensionResource(R.dimen.small_medium_padding),
                        bottom = dimensionResource(R.dimen.small_medium_padding)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(country.descRes),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.fillMaxWidth(0.85f) //fix vystredenie justified textu
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExplorerPreview() {
    PathPulseTheme {
        ExplorerScreen()
    }
}
