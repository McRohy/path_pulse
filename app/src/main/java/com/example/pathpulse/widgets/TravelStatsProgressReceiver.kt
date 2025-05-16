package com.example.pathpulse.widgets

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

/**
https://developer.android.com/codelabs/glance#0
https://www.youtube.com/watch?v=bhrN7yFG0D4
 **/
class TravelStatsProgressReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = TravelStatsProgress()
}