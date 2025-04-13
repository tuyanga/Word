package com.example.newword.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newword.data.SettingsDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsDataStore: SettingsDataStore) : ViewModel() {

    val displayMode: StateFlow<String> = settingsDataStore.displayMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "both")

    fun saveDisplayMode(mode: String) {
        viewModelScope.launch {
            settingsDataStore.saveDisplayMode(mode)
        }
    }
}
