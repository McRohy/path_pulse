package com.example.pathpulse.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController

import com.example.pathpulse.ui.theme.PathPulseTheme


@Composable
fun AddScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {

}

@Preview(showBackground = true)
@Composable
fun AddScreenPreview() {
    PathPulseTheme  {
        //AddScreen()
    }
}
