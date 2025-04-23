package com.example.pathpulse.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.StackedBarChart
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.pathpulse.R


/**
prednaska 6
 **/

interface MyAppDestination {
    val title: Int
    val route: String
    val icon: ImageVector
}

object ExplorerDestination : MyAppDestination {
    override val title = R.string.explorer
    override val route = "explorer"
    override val icon = Icons.Default.LocationOn
}

object StatsDestination : MyAppDestination {
    override val title = R.string.stats
    override val route = "stats"
    override val icon = Icons.Default.StackedBarChart
}

object MemoriesDestination : MyAppDestination {
    override val title = R.string.memories
    override val route = "memories"
    override val icon = Icons.Default.Star
}

object AddDestination : MyAppDestination {
    override val title = R.string.add
    override val route = "add"
    override val icon = Icons.Default.Add
}

