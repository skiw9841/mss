package com.example.mss

import android.app.Application
import android.content.Context
import com.example.mss.di.apiModule
import com.example.mss.di.dbModule
import com.example.mss.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MssApp : Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Android context
            androidContext(this@MssApp)
            // modules
            modules(listOf(apiModule, dbModule, repositoryModule))
        }

    }
}