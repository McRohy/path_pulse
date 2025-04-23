package com.example.pathpulse.screens

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pathpulse.AppViewModelProvider
import com.example.pathpulse.ui.theme.PathPulseTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MemoriesScreen(
    navController: NavHostController,
    viewModel: MemoriesViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier,
) {

}

@Preview(showBackground = true)
@Composable
fun MemoriesPreview() {
    PathPulseTheme  {
       //MemoriesScreen()
    }
}