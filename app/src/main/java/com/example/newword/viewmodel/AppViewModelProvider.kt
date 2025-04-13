package com.example.newword.viewmodel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.newword.newwordApplication

object AppViewModelProvider {

    val Factory = viewModelFactory {
        initializer {
            val app = this.newwordApplication()
            WordViewModel(app.container.wordsRepo)
        }
        initializer {
            val app = this.newwordApplication()
            SettingsViewModel(app.container.settingsDataStore)
        }
    }
    }

    fun CreationExtras.newwordApplication(): newwordApplication =
        (this[AndroidViewModelFactory.APPLICATION_KEY] as newwordApplication)
