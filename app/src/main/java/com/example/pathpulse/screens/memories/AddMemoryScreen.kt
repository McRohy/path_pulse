package com.example.pathpulse.screens.memories

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.pathpulse.AppViewModelProvider
import com.example.pathpulse.helpers.PhotoPickerHelper
import com.example.pathpulse.R
import com.example.pathpulse.ui.theme.PathPulseTheme
import kotlinx.coroutines.launch

/**
 * Obrazovka pre pridanie novej spomienky (krajiny) do kolekcie.
 *
 * @param modifier Modifier pre prispôsobenie rozloženia.
 * @param viewModel ViewModel poskytujúci logiku a stav obrazovky.
 */
@SuppressLint("ContextCastToActivity")
@Composable
fun AddScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    //pre Photo Picker
    val activity = LocalContext.current as ComponentActivity
    val photoPicker = rememberSaveable(
        saver = PhotoPickerHelper.saver(activity)
    ) {
        PhotoPickerHelper(activity)
    }

    if (uiState.searchActive) {
        Box(modifier = modifier.fillMaxSize())
        {
            AddSearchBar(
                query = uiState.searchQuery,
                viewModel = viewModel
            )
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(dimensionResource(R.dimen.medium_padding))
        ) {

            AddSearchBar(
                query = uiState.searchQuery,
                viewModel = viewModel
            )

            Spacer(Modifier.height(dimensionResource(R.dimen.large_spacer)))

            Text(
                text = stringResource(R.string.describe_your_memory),
                style = MaterialTheme.typography.headlineSmall
            )

            OutlinedTextField(
                value = uiState.countryDetails.description,
                onValueChange = viewModel::onDescriptionChange,
                label = {
                    Text(
                        text = stringResource(R.string.my_journey_to),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 150.dp, max = 250.dp),
                maxLines = 10,
                shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_shape_corner)),
            )

            Spacer(Modifier.height(dimensionResource(R.dimen.large_spacer)))

            // Picker
            photoPicker.PhotoUploader(Modifier.fillMaxWidth()) { uri ->
                viewModel.onImagePicked(uri)
            }

            Spacer(Modifier.height(dimensionResource(R.dimen.small_spacer)))

            // Preview
            photoPicker.selectedImageUri?.let { uri ->
                AsyncImage(
                    model = uri, contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.height(dimensionResource(R.dimen.large_spacer)))

            Text(
                text = stringResource(R.string.rate_your_journey),
                style = MaterialTheme.typography.headlineSmall
            )

            RatingBar(
                rating = uiState.countryDetails.rating,
                onRatingChanged = viewModel::onRatingChange,
            )

            Spacer(Modifier.height(dimensionResource(R.dimen.large_spacer)))

            Button(
                enabled = uiState.isEntryValid,
                onClick = {
                    coroutineScope.launch {
                        viewModel.save()
                        navigateBack()
                    }
                },
                shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_shape_corner)),

                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp, max = 200.dp)
            ) {
                Text(
                    text = stringResource(R.string.add_in_collection),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

/**
 * Vyhľadávací panel pre filtrovanie krajín podľa mena.
 *
 * @param query Aktuálny textový dotaz pre vyhľadávanie.
 * @param viewModel ViewModel reagujúci na zmeny v dotaze a spracúvajúci výber.
 *
 * https://www.youtube.com/watch?v=fKtCP9TcPaw
 */
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
        onSearch = {},
        active = uiState.searchActive,
        onActiveChange = { isActive ->
            viewModel.onSearchBarActiveChange(isActive)
        },
        placeholder = {
            Text(
                text = stringResource(R.string.find_your_country),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (uiState.searchActive) {
                IconButton(onClick = {
                    if (uiState.searchQuery.isNotEmpty()) {
                        viewModel.onSearchQueryChange("")
                    } else {
                        viewModel.onSearchBarActiveChange(false)
                    }
                }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null
                    )
                }
            }
        },
        modifier = Modifier
            .clip(RoundedCornerShape(dimensionResource(R.dimen.rounded_shape_corner)))
            .fillMaxWidth()

    ) {
        LazyColumn {
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
                        .padding(dimensionResource(R.dimen.small_padding))
                )
            }
        }
    }
}

/**
 * Zobrazuje riadok hviezdičkového hodnotenia.
 *
 * @param rating Aktuálne hodnotenie (0–10). Počet vyplnených hviezdičiek = rating.
 * @param modifier Modifier pre prispôsobenie rozloženia hodnotenia.
 *
 * https://www.youtube.com/watch?v=bhlQq5s0WHw
 */
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Int = 0,
    onRatingChanged: (Int) -> Unit,
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
                    .size(dimensionResource(R.dimen.size_star))
                    .clickable { onRatingChanged(index) }
            )
        }
    }
}

/**
 * Náhľad obrazovky AddScreen.
 */
@Preview(showBackground = true)
@Composable
fun AddScreenPreview() {
    PathPulseTheme {
        AddScreen(navigateBack = {})
    }
}

