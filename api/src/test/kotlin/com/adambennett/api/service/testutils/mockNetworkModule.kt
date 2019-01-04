package com.adambennett.api.service.testutils

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.koin.dsl.module.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val API_TIMEOUT = 1L

fun mockNetworkModule(server: MockWebServer) = module {

    single { Moshi.Builder().build() }

    single<CallAdapter.Factory> { RxJava2CallAdapterFactory.create() }

    single<Converter.Factory> { MoshiConverterFactory.create(get()) }

    single {
        OkHttpClient.Builder()
            .connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(server.url("/"))
            .client(get())
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .build()
    }
}