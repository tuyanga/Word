package com.example.newword

import android.app.Application
import com.example.newword.data.AppContainer
import com.example.newword.data.AppDataContainer

class newwordApplication : Application(){
    lateinit var container : AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}