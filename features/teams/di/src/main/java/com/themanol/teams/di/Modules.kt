package com.themanol.teams.di

import com.themanol.reactbasket.teams.data.datasource.TeamDataSourceImpl
import com.themanol.reactbasket.teams.data.datasource.TeamRemoteDataSource
import com.themanol.reactbasket.teams.data.repository.TeamRepositoryImpl
import com.themanol.reactbasket.teams.domain.repository.TeamRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.core.module.Module
import org.koin.dsl.module

@ExperimentalCoroutinesApi
@UseExperimental(FlowPreview::class)
val repositoryModule: Module = module {
    single { TeamRepositoryImpl(remoteDataSource = get() ) as TeamRepository }
}

val dataSourceModule: Module = module {
    single { TeamDataSourceImpl(service = get()) as TeamRemoteDataSource }
}

val serviceModule = com.themanol.reactbasket.teams.data.di.serviceModule

