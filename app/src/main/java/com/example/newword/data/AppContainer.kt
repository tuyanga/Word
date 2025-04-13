package com.example.newword.data

import android.content.Context

interface AppContainer {
    val wordsRepo: WordRepo
    val settingsDataStore: SettingsDataStore
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val wordsRepo: WordRepo by lazy {
        OfflineWordRepo(WordDatabase.getDatabase(context).wordDao())
    }
    override val settingsDataStore: SettingsDataStore by lazy {
        SettingsDataStore(context)
    }
}