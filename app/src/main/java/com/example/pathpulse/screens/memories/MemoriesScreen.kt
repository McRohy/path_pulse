package com.example.pathpulse.screens.memories

import android.annotation.SuppressLint
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
import androidx.navigation.NavHostController
import com.example.pathpulse.AppViewModelProvider
import com.example.pathpulse.R
import com.example.pathpulse.navigation.OneMemoryDestination
import com.example.pathpulse.ui.theme.PathPulseTheme

@Composable
fun MemoriesScreen(
    viewModel: MemoriesViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateToMemory: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val countriesList by viewModel.countriesList.collectAsState()
//    val selectedCountry by viewModel.selectedCountry.collectAsState()

//    Box(modifier = modifier.fillMaxSize())
//    {

            LazyVerticalGrid (
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp),
            ) {
                items(countriesList) { country ->

                    Button(
                     onClick = { navigateToMemory(country.id) },
                     shape = RoundedCornerShape(dimensionResource(R.dimen.rounded_shape_corner)),
                     colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary),
                     modifier = Modifier
                         .fillMaxWidth()
                         .height(200.dp)
                         .padding(6.dp)
                    )
                    {
                        Text(text = country.name,
                             style = MaterialTheme.typography.headlineSmall,
                             textAlign = TextAlign.Center
                            )

                    }


                }
            }
//        } else {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//
//            ) {
//                DetailCard(
//                    country = selectedCountry!!,
//                    onClose = { viewModel.selectCountry(null) }
//                )
//
//
//            }

//        }


//    }
}



//@Composable
//fun CountryCard(
//    country: CountryEntity,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(100.dp)
//            .clickable(onClick = onClick)
//            .padding( 6.dp)
//            .border(
//                6.dp,
//                MaterialTheme.colorScheme.primary,
//                RoundedCornerShape(16.dp)
//            ),
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(text = country.name,
//                style = MaterialTheme.typography.headlineSmall,
//                modifier = modifier.padding(12.dp))
//        }
//    }
//}
//
//@Composable
//fun DetailCard(
//    country: CountryEntity,
//    onClose: () -> Unit,
//    modifier: Modifier = Modifier )
//{
//    Card(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding( 6.dp)
//            .border(
//                6.dp,
//                MaterialTheme.colorScheme.primary,
//                RoundedCornerShape(16.dp)
//            ),
//    )
//    {
//        val scrollState = rememberScrollState()
//
//        Column (modifier = Modifier
//            .fillMaxWidth()
//            .verticalScroll(scrollState)
//            .padding(12.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
//
//            Row (
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween)
//            {
//                Text(text = country.name,
//                    style = MaterialTheme.typography.headlineMedium,
//                    modifier = Modifier.padding(6.dp)
//                )
//
//                Button(
//                    onClick = onClose,
//                    shape = RoundedCornerShape(16.dp),
//
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = MaterialTheme.colorScheme.primary
//                    )
//
//                )
//                {
//                    Icon(
//                        imageVector   = Icons.Filled.Close,
//                        contentDescription = "Close"
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            Text(text = country.description!!,
//                style = MaterialTheme.typography.bodyLarge,
//                modifier = Modifier.padding(6.dp)
//            )
//
//        }
//
//
//    }
//
//
//}



@Preview(showBackground = true)
@Composable
fun MemoriesPreview() {
   // val country = CountryEntity(id = 1, name = "Slovensko", description = "Krásna krajina pod Tatrami", 0, rating =0)
    PathPulseTheme {
       // MemoriesScreen()
    }
}