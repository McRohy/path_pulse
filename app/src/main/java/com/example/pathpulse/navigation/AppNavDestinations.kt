package com.example.pathpulse.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.StackedBarChart
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.pathpulse.R

/**
 * Rozhranie reprezentujúce destináciu (cieľ) v rámci navigácie aplikácie.
 *
 * @property title ID reťazcového zdroja pre názov destinácie.
 * @property route Jedinečná cesta (route) pre navigáciu k tejto destinácii.
 * @property icon Vektorová ikonka reprezentujúca túto destináciu v UI (pre bottomNav).
 *
 * prednáška 6 Navigácia, Architektúra UI
 */
interface MyAppDestination {
    val title: Int
    val route: String
    val icon: ImageVector
}

/**
 * Destinácia pre obrazovku Explorer.
 */
object ExplorerDestination : MyAppDestination {
    override val title = R.string.explorer
    override val route = "explorer"
    override val icon = Icons.Default.LocationOn
}

/**
 * Destinácia pre obrazovku Travel Stats.
 */
object StatsDestination : MyAppDestination {
    override val title = R.string.stats
    override val route = "stats"
    override val icon = Icons.Default.StackedBarChart
}

/**
 * Destinácia pre obrazovku Memories.
 */
object MemoriesDestination : MyAppDestination {
    override val title = R.string.memories
    override val route = "memories"
    override val icon = Icons.Default.Star
}

/**
 * Destinácia pre obrazovku Add.
 */
object AddDestination : MyAppDestination {
    override val title = R.string.add
    override val route = "add"
    override val icon = Icons.Default.Add
}

/**
 * Destinácia pre detail jednej spomienky s možnosťou načítania podľa ID.
 */
object OneMemoryDestination : MyAppDestination {
    override val title = R.string.add
    override val route = "memory"

    const val memoryIdArg = "memoryIdArg"
    val routeWithArgs = "$route/{$memoryIdArg}"

    override val icon = Icons.Default.Star
}

