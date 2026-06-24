package com.example.taskie

import android.app.Application
import com.example.taskie.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class TaskieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TaskieApplication)
            modules(appModule)
        }
    }
}