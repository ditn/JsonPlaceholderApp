package com.adambennett.api.koin

import com.adambennett.api.api.PlaceholderEndpoints
import com.adambennett.api.service.PlaceholderService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    single { get<Retrofit>().create(PlaceholderEndpoints::class.java) }

    single { PlaceholderService(get()) }
}
