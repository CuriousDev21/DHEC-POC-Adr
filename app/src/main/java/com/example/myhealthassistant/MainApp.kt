package com.example.myhealthassistant

import android.app.Application
import com.example.myhealthassistant.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Koin for dependency injection
        startKoin {
            // Provide Android context
            androidContext(this@MainApp)
            // Load modules
            modules(appModule)
        }
    }
}
