package com.themanol.reactbasket.teams.data.datasource

import com.themanol.reactbasket.data.PaginatedDataEntity
import com.themanol.reactbasket.data.TeamEntity

interface TeamRemoteDataSource {

    suspend fun get(): PaginatedDataEntity<List<TeamEntity>>

    suspend fun get(teamId: Int): TeamEntity
}
