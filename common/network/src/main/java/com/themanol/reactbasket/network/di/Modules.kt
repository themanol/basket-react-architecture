package com.themanol.reactbasket.network.di

import com.themanol.reactbasket.network.createNetworkClient
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule: Module = module {
    single {
        retrofit
    }
}

private const val BASE_URL = "https://balldontlie.io/api/v1/"

private val retrofit: Retrofit = createNetworkClient(BASE_URL)