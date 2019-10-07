package com.themanol.reactbasket.teams.data.di

import com.themanol.reactbasket.teams.data.datasource.TeamService
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule: Module = module {
    single { (get() as Retrofit).create(TeamService::class.java)}
}