package com.example.pathpulse.widgets

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.appwidget.LinearProgressIndicator
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.components.TitleBar
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.fillMaxSize
import androidx.glance.unit.ColorProvider
import com.example.pathpulse.R
import com.example.pathpulse.data.dataMomories.AppDataContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

/**
https://developer.android.com/codelabs/glance#0
https://www.youtube.com/watch?v=bhrN7yFG0D4
 **/

class TravelStatsProgress : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val title = context.getString(R.string.widget_title_progress)

        val container = AppDataContainer(context.applicationContext)
        val repo = container.countriesRepository

        val numberOfCountriesVisited = withContext(Dispatchers.IO) {
            repo.getCountriesCount()
                .firstOrNull()
                ?: 0
        }

        provideContent {
            GlanceTheme {
                Content(numberOfCountriesVisited, title)
            }
        }
    }

    @Composable
    private fun Content(numberOfCountriesVisited: Int, title: String) {
        Scaffold(
            titleBar = {
                TitleBar(
                    startIcon = ImageProvider(R.drawable.ic_launcher_foreground),
                    title = title
                )
            },
            modifier = GlanceModifier.fillMaxSize()
        ) {
            val progress = numberOfCountriesVisited / 195f
            LinearProgressIndicator(
                progress = progress,
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .height(16.dp),
                color = ColorProvider(Color(0xFF08677F)),
                backgroundColor = ColorProvider(Color.LightGray)
            )
        }
    }
}