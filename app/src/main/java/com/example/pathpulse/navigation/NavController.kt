package com.example.pathpulse.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pathpulse.screens.explorer.ExplorerScreen
import com.example.pathpulse.screens.memories.MemoriesScreen
import com.example.pathpulse.screens.TravelStatsScreen
import com.example.pathpulse.screens.memories.AddScreen
import com.example.pathpulse.screens.memories.MemoryDetailScreen

/**
prednaska 6 a https://www.youtube.com/watch?v=O9csfKW3dZ4
 **/

@OptIn(ExperimentalMaterial3Api::class)
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
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            var shouldShowTopBar = false
            var titleResId = 0

            for (navItemTitle in bottomNavItems) {
                if (navItemTitle.route == currentRoute) {
                    shouldShowTopBar = true
                    titleResId = navItemTitle.title
                    break
                }

            }

            if (shouldShowTopBar) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = titleResId),
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
            }

            if (currentRoute == AddDestination.route || currentRoute == OneMemoryDestination.routeWithArgs) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Your memory",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Späť")
                        }
                    }
                )
            }
        },

        floatingActionButton = {
            if (currentRoute == MemoriesDestination.route) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(AddDestination.route)
                    },
                    modifier = Modifier.padding(
                        end = WindowInsets.safeDrawing
                            .asPaddingValues()
                            .calculateEndPadding(LocalLayoutDirection.current)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }
        },

        bottomBar = {
            if (currentRoute in bottomNavItems.map { it.route }) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.contentColorFor(containerColor)
                ) {
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
                TravelStatsScreen(modifier = modifier)
            }
            composable(MemoriesDestination.route) {
                MemoriesScreen(
                    navigateToMemory = {
                        navController.navigate("${OneMemoryDestination.route}/${it}")
                    },
                    modifier = modifier
                )
            }
            composable(route = AddDestination.route) {
                AddScreen(
                    navigateBack = { navController.popBackStack() },
                    modifier = modifier
                )
            }

            composable(
                route  = OneMemoryDestination.routeWithArgs,
                arguments = listOf(navArgument(OneMemoryDestination.memoryIdArg) {
                        type = NavType.IntType
                    }
                )
            ) {
                MemoryDetailScreen(
                    navigateBack = { navController.popBackStack() },
                    modifier = modifier,
                )
            }
        }
    }
}

@Composable
private fun currentRouteDes(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
