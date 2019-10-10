package com.themanol.reactbasket.teams.data.datasource

import com.themanol.reactbasket.data.DataEntity
import com.themanol.reactbasket.data.TeamEntity
import io.reactivex.Single

class TeamDataSourceImpl(private val service: TeamService) : TeamRemoteDataSource {

    override fun get(): Single<DataEntity<List<TeamEntity>>> =
        service.getTeams()

    override fun get(teamId: Int): Single<TeamEntity> =
       service.getTeam(teamId)
}
