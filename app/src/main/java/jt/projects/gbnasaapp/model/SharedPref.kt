package jt.projects.gbnasaapp.model

import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import jt.projects.gbnasaapp.R


class SharedPref(private val sharedPreferences: SharedPreferences, private val dbKey: String) {
    lateinit var settings: Settings

    fun getData(): Settings {
        val jsonNotes = sharedPreferences.getString(dbKey, null)
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
        sharedPreferences.edit().putString(dbKey, jsonNotes).apply()
    }
}

// класс хранения настроек в формате JSON
data class Settings(
    var theme: Int = R.style.Theme_GBNasaApp, //default
    var podHD: Boolean = false,
    var marsPhotoDaysBefore: Int = 7 // за сколько дней до текущей даты показывать фото марса
)
