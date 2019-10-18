package com.themanol.reactbasket.teams.data.datasource

import com.themanol.reactbasket.data.PaginatedDataEntity
import com.themanol.reactbasket.data.TeamEntity

class TeamDataSourceImpl(private val service: TeamService) : TeamRemoteDataSource {

    override suspend fun get(): PaginatedDataEntity<List<TeamEntity>> =
        service.getTeams()

    override suspend fun get(teamId: Int): TeamEntity =
        service.getTeam(teamId)
}
