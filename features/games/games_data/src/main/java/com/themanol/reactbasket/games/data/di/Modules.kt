package com.themanol.reactbasket.games.data.di

import com.themanol.reactbasket.games.data.datasource.GameService
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule: Module = module {
    single { (get() as Retrofit).create(GameService::class.java)}
}