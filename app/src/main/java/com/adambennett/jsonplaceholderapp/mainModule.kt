package com.adambennett.jsonplaceholderapp

import com.adambennett.network.interfaces.Logger
import org.koin.dsl.module.module
import timber.log.Timber

val mainModule = module {

    factory {
        object : Logger {
            override fun d(s: String) {
                Timber.d(s)
            }

            override fun v(s: String) {
                Timber.v(s)
            }

            override fun e(s: String) {
                Timber.e(s)
            }
        } as Logger
    }
}