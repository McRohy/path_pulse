package com.example.pathpulse.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.pathpulse.ui.theme.PathPulseTheme

@Composable
fun TravelStatsScreen(modifier: Modifier = Modifier) {

}

@Preview(showBackground = true)
@Composable
fun StatCardPreview() {
    PathPulseTheme{
        TravelStatsScreen()
    }
}