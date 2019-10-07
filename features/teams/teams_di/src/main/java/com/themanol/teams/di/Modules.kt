package com.themanol.teams.di

import com.themanol.reactbasket.network.createNetworkClient
import com.themanol.reactbasket.teams.data.datasource.TeamDataSourceImpl
import com.themanol.reactbasket.teams.data.datasource.TeamRemoteDataSource
import com.themanol.reactbasket.teams.data.datasource.TeamService
import com.themanol.reactbasket.teams.data.repository.TeamRepositoryImpl
import com.themanol.reactbasket.teams.domain.repository.TeamRepository
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val repositoryModule: Module = module {
    single { TeamRepositoryImpl(remoteDataSource = get() ) as TeamRepository }
}

val dataSourceModule: Module = module {
    single { TeamDataSourceImpl(service = teamService) as TeamRemoteDataSource }
}

val networkModule: Module = module {
    single { teamService }
}

private const val BASE_URL = "https://balldontlie.io/api/v1/"

private val retrofit: Retrofit = createNetworkClient(BASE_URL)
private val teamService: TeamService = retrofit.create(TeamService::class.java)