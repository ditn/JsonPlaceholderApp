package com.adambennett.network

import com.squareup.moshi.Moshi
import java.util.concurrent.TimeUnit
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

private const val API_TIMEOUT = 30L
private const val API_ROOT = "https://jsonplaceholder.typicode.com"

val networkModule = module {

    single { LoggingInterceptor(get()) as Interceptor }

    single { Moshi.Builder().build() }

    single<CallAdapter.Factory> { RxJava2CallAdapterFactory.create() }

    single<Converter.Factory> { MoshiConverterFactory.create(get()) }

    single {
        OkHttpClient.Builder()
            .connectionSpecs(listOf(ConnectionSpec.MODERN_TLS))
            .connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(get())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(API_ROOT)
            .client(get())
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .build()
    }
}
