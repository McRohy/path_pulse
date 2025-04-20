package com.example.pathpulse.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pathpulse.screens.ExplorerScreen
import com.example.pathpulse.screens.MemoriesScreen
import com.example.pathpulse.screens.StatsScreen
import com.example.pathpulse.screens.AddScreen

/**
 prednaska 6 a https://www.youtube.com/watch?v=O9csfKW3dZ4
 **/

@Composable
fun NavController(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    val bottomNavItems = listOf(
        ExplorerDestination,
        StatsDestination,
        MemoriesDestination
    )

    val currentRoute = currentRouteDes(navController)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (currentRoute in listOf("explorer", "stats", "memories")) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(imageVector = item.icon, contentDescription = null) },
                            label = { Text(text = " ") }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = StatsDestination.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(ExplorerDestination.route) {
                ExplorerScreen(modifier = modifier)
            }
            composable(StatsDestination.route) {
                StatsScreen(modifier = modifier)
            }
            composable(MemoriesDestination.route) {
                MemoriesScreen(
                    modifier = modifier,
                    onNavigateToAddScreen = { navController.navigate(AddDestination.route) }
                )
            }
            composable(AddDestination.route) {
                AddScreen(
                    modifier = modifier,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}

// Pomocná funkcia na získanie aktuálnej navigačnej cesty.
// Získa aktuálnu cestu v NavHostControlleri.
@Composable
private fun currentRouteDes(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
