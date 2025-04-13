package com.example.newword.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("settings")

class SettingsDataStore(private val context: Context) {
    companion object SettingsKeys {
        val DISPLAY_MODE = stringPreferencesKey("display_mode")
    }
    val displayMode: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[SettingsKeys.DISPLAY_MODE] ?: "both"
    }

    suspend fun saveDisplayMode(mode: String) {
        context.dataStore.edit { preferences ->
            preferences[SettingsKeys.DISPLAY_MODE] = mode
        }
    }
}