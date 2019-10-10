package com.themanol.reactbasket.players.di

import com.themanol.reactbasket.players.data.repository.PlayersRepositoryImpl
import com.themanol.reactbasket.players.domain.repository.PlayersRepository
import com.themanol.reactbasket.teams.data.datasource.PlayerDataSourceImpl
import com.themanol.reactbasket.teams.data.datasource.PlayerRemoteDataSource
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module = module {
    single { PlayersRepositoryImpl(remoteDataSource = get()) as PlayersRepository }
}

val dataSourceModule: Module = module {
    single { PlayerDataSourceImpl(service = get()) as PlayerRemoteDataSource }
}

val serviceModule = com.themanol.reactbasket.players.data.di.serviceModule


