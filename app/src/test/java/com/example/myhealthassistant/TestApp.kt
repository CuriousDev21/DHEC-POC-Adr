package com.example.myhealthassistant

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin

class TestApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TestApp)
            modules(emptyList())
        }
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }
}