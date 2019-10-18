package com.themanol.reactbasket.games.di

import com.themanol.reactbasket.data.PagerImpl
import com.themanol.reactbasket.games.data.repository.GamesRepositoryImpl
import com.themanol.reactbasket.games.domain.repository.GamesRepository
import com.themanol.reactbasket.teams.data.datasource.GameDataSourceImpl
import com.themanol.reactbasket.teams.data.datasource.GameRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.core.module.Module
import org.koin.dsl.module

@ExperimentalCoroutinesApi
@UseExperimental(FlowPreview::class)
val repositoryModule: Module = module {
    single { GamesRepositoryImpl(remoteDataSource = get(), pager = PagerImpl()) as GamesRepository }
}

val dataSourceModule: Module = module {
    single { GameDataSourceImpl(service = get()) as GameRemoteDataSource }
}

val serviceModule = com.themanol.reactbasket.games.data.di.serviceModule


