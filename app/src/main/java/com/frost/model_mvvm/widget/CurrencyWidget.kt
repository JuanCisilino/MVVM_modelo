package com.frost.model_mvvm.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.frost.model_mvvm.R

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

        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        intent?.let {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(context, 0, it, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent)
        }

        val id = appWidgetManager.getAppWidgetIds(ComponentName(context, CurrencyWidget::class.java))

        getDataAndSetView(context, views)

        appWidgetManager.updateAppWidget(id, views)
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
    val blue = sharedPreferences?.getString("Dolar Blue", "0.0")
    val oficial = sharedPreferences?.getString("Dolar Oficial", "0.0")
    val minorista = sharedPreferences?.getString("Dolar turista", "0.0")
    // Construct the RemoteViews object
    views.setTextViewText(R.id.appwidget_blue, "Blue\n$$blue")
    views.setTextViewText(R.id.appwidget_oficial, "Oficial\n$$oficial")
    views.setTextViewText(R.id.appwidget_minorista, "Turista\n$$minorista")

}