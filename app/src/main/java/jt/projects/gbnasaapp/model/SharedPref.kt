package jt.projects.gbnasaapp.model

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import jt.projects.gbnasaapp.R

private const val SP_DB_NAME = "settings"
private const val SP_DB_KEY = "ALL_SETTINGS_IN_JSON_FORMAT"

class SharedPref {
    companion object {
        lateinit var settings: Settings
        private lateinit var sharedPref: SharedPreferences

        fun initSharedPreferencesContext(context: Context) {
            sharedPref = context.getSharedPreferences(SP_DB_NAME, Context.MODE_PRIVATE)
        }

        fun getData(): Settings {
            val jsonNotes = sharedPref.getString(SP_DB_KEY, null)
            val type = object : TypeToken<Settings>() {}.type
            val data = GsonBuilder().create().fromJson<Any>(jsonNotes, type)
            settings = if (data == null) {
                Settings()
            } else data as Settings
            return settings
        }

        fun save() {
            var jsonNotes: String? = ""
            settings?.let {
                jsonNotes = GsonBuilder().create().toJson(it)
            }
            sharedPref.edit().putString(SP_DB_KEY, jsonNotes).apply()
        }
    }

    // класс хранения настроек в формате JSON
    data class Settings(
        var theme: Int = R.style.Theme_GBNasaApp, //default
        var podHD: Boolean = false
    )
}