package com.example.pathpulse.screens.explorer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pathpulse.R
import com.example.pathpulse.data.dataExplorer.CountryExplorer
import com.example.pathpulse.data.dataExplorer.DataSource
import com.example.pathpulse.ui.theme.PathPulseTheme

/**
 * Zobrazuje zoznam krajín vo forme vertikálneho scrollovateľného zoznamu.
 *
 * @param modifier Modifier pre prispôsobenie rozloženia.
 */
@Composable
fun ExplorerScreen(
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

/**
 * Zobrazuje kartu s obrázkom, názvom a rozbaľovacím popisom krajiny.
 *
 * Karta obsahuje: obrázok krajiny, názov krajiny a tlačidlo na rozbalenie/skrytie popisu,
 * voliteľný textový popis, ktorý sa zobrazí po rozbalení.
 *
 * @param country Dáta krajiny.
 * @param modifier Modifier pre prispôsobenie rozloženia karty.
 */
@Composable
fun ExplorerCard(
    country: CountryExplorer,
    modifier: Modifier = Modifier
) {
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
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.small_medium_padding)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(country.titleRes),
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(dimensionResource(R.dimen.size_expand_icon))
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
                    modifier = Modifier.fillMaxWidth(0.8f) //vystredenie justified textu
                )
            }
        }
    }
}

/**
 * Náhľad obrazovky ExplorerScreen.
 */
@Preview(showBackground = true)
@Composable
fun ExplorerPreview() {
    PathPulseTheme {
        ExplorerScreen()
    }
}

/**
 * Náhľad karty ExplorerCard s ukážkovými dátami.
 */
@Preview(showBackground = true)
@Composable
fun ExplorerCardPreview() {
    PathPulseTheme {
        ExplorerCard(
            country = CountryExplorer(
                R.drawable.explorer_uae_lowquality_blur,
                R.string.uae_TOP,
                R.string.uae_desc
            ),
        )
    }
}