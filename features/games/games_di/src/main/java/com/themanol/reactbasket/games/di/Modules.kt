package com.themanol.reactbasket.games.di

import com.themanol.reactbasket.games.data.datasource.GameService
import com.themanol.reactbasket.games.data.repository.GamesRepositoryImpl
import com.themanol.reactbasket.games.domain.repository.GamesRepository
import com.themanol.reactbasket.network.createNetworkClient
import com.themanol.reactbasket.teams.data.datasource.GameDataSourceImpl
import com.themanol.reactbasket.teams.data.datasource.GameRemoteDataSource
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val repositoryModule: Module = module {
    single { GamesRepositoryImpl(remoteDataSource = get() ) as GamesRepository }
}

val dataSourceModule: Module = module {
    single { GameDataSourceImpl(service = gameService) as GameRemoteDataSource }
}

val networkModule: Module = module {
    single { gameService }
}

private const val BASE_URL = "https://balldontlie.io/api/v1/"

private val retrofit: Retrofit = createNetworkClient(BASE_URL)
private val gameService: GameService = retrofit.create(GameService::class.java)