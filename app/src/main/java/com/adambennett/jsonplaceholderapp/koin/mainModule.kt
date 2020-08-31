package com.adambennett.jsonplaceholderapp.koin

import com.adambennett.network.interfaces.Logger
import org.koin.dsl.module
import timber.log.Timber

val mainModule = module {

    factory<Logger> {
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
        }
    }
}
