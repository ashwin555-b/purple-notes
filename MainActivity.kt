package com.example.purplenotes

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    private val prefsName = "purple_notes"
    private val noteKey = "note_text"
    private lateinit var editor: EditText
    private lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(22), dp(28), dp(22), dp(22))
            setBackgroundColor(0xFF100B18.toInt())
        }

        val title = TextView(this).apply {
            text = "PURPLE NOTES"
            textSize = 13f
            letterSpacing = 0.18f
            setTextColor(0xFFC9A7FF.toInt())
        }
        root.addView(title, matchWrap())

        val subtitle = TextView(this).apply {
            text = "Your thoughts, in one place."
            textSize = 24f
            setTextColor(0xFFF7F0FF.toInt())
            setPadding(0, dp(8), 0, dp(18))
        }
        root.addView(subtitle, matchWrap())

        editor = EditText(this).apply {
            hint = "Write something…"
            setHintTextColor(0xFF9B8BAA.toInt())
            setTextColor(0xFFF7F0FF.toInt())
            textSize = 17f
            gravity = Gravity.TOP or Gravity.START
            minLines = 10
            setPadding(dp(16), dp(16), dp(16), dp(16))
            setBackgroundColor(0xFF21172D.toInt())
            setText(getSavedNote())
        }
        root.addView(editor, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f
        ))

        status = TextView(this).apply {
            text = "Saved automatically"
            textSize = 12f
            setTextColor(0xFFBFA9D6.toInt())
            setPadding(0, dp(10), 0, dp(12))
        }
        root.addView(status, matchWrap())

        val clear = Button(this).apply {
            text = "Clear note"
            setTextColor(0xFFFFFFFF.toInt())
            setBackgroundColor(0xFF7541B8.toInt())
            setOnClickListener {
                editor.setText("")
                saveNote("")
            }
        }
        root.addView(clear, matchWrap())

        setContentView(root)

        editor.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                saveNote(s?.toString().orEmpty())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun getSavedNote(): String =
        getSharedPreferences(prefsName, Context.MODE_PRIVATE).getString(noteKey, "") ?: ""

    private fun saveNote(text: String) {
        getSharedPreferences(prefsName, Context.MODE_PRIVATE)
            .edit().putString(noteKey, text).apply()
        status.text = "Saved automatically · ${text.length} characters"
        val manager = AppWidgetManager.getInstance(this)
        val ids = manager.getAppWidgetIds(ComponentName(this, NotesWidgetProvider::class.java))
        if (ids.isNotEmpty()) {
            NotesWidgetProvider.updateAll(this, manager, ids)
        }
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()
    private fun matchWrap() = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
    )
}
