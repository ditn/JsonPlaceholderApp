package com.adambennett.jsonplaceholderapp

import android.app.Application
import com.adambennett.api.koin.apiModule
import com.adambennett.jsonplaceholderapp.koin.mainModule
import com.adambennett.jsonplaceholderapp.koin.viewModelModule
import com.adambennett.network.networkModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class PlaceholderApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        startKoin(
            this,
            listOf(networkModule, apiModule,
                viewModelModule,
                mainModule
            )
        )
    }
}