package com.example.pathpulse.widgets

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

/**
 * GlanceAppWidgetReceiver pre widget zobrazujúci štatistiku progressu.
 *
 * https://developer.android.com/codelabs/glance#0
 * https://developer.android.com/develop/ui/views/appwidgets#valuesstyles.xml
 **/
class TravelStatsProgressReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = TravelStatsProgress()
}