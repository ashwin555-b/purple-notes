package com.example.purplenotes

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class NotesWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        manager: AppWidgetManager,
        appWidgetIds: IntArray
    ) = updateAll(context, manager, appWidgetIds)

    companion object {
        fun updateAll(context: Context, manager: AppWidgetManager, ids: IntArray) {
            val prefs = context.getSharedPreferences("purple_notes", Context.MODE_PRIVATE)
            val note = prefs.getString("note_text", "").orEmpty()
            val views = RemoteViews(context.packageName, R.layout.notes_widget)
            views.setTextViewText(
                R.id.widget_note,
                if (note.isBlank()) "Tap EDIT to write your first note ✦"
                else note
            )

            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            val pendingIntent = PendingIntent.getActivity(
                context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_note, pendingIntent)
            views.setOnClickPendingIntent(R.id.widget_edit, pendingIntent)
            views.setOnClickPendingIntent(R.id.widget_header, pendingIntent)
            manager.updateAppWidget(ids, views)
        }
    }
}
