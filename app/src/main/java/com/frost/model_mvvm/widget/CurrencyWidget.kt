package com.frost.model_mvvm.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION_CODES
import android.widget.RemoteViews
import androidx.annotation.RestrictTo
import com.frost.model_mvvm.R
import com.frost.model_mvvm.ui.MainActivity

/**
 * Implementation of App Widget functionality.
 */
class CurrencyWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        if (appWidgetIds.isEmpty()) return

        val views = RemoteViews(context.packageName, R.layout.currency_widget)
        val widgetPendingIntent = PendingIntent
            .getBroadcast(context, 0, Intent(context, MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE)
        views.setOnClickPendingIntent(R.id.widget_layout, widgetPendingIntent)

        getDataAndSetView(context, views)

        appWidgetManager.updateAppWidget(appWidgetIds[0], views)
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun getDataAndSetView(context: Context, views: RemoteViews){
    val sharedPreferences = context.getSharedPreferences("prefs_file", Context.MODE_PRIVATE)
    val blue = sharedPreferences?.getString("blue", "0.0")
    val oficial = sharedPreferences?.getString("oficial", "0.0")
    val minorista = sharedPreferences?.getString("minorista", "0.0")
    // Construct the RemoteViews object
    views.setTextViewText(R.id.appwidget_blue, "Blue\n$$blue")
    views.setTextViewText(R.id.appwidget_oficial, "Oficial\n$$oficial")
    views.setTextViewText(R.id.appwidget_minorista, "Minorista\n$$minorista")

}