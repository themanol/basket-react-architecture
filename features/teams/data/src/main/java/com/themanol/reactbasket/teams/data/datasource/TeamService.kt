package com.themanol.reactbasket.teams.data.datasource

import com.themanol.reactbasket.data.PaginatedDataEntity
import com.themanol.reactbasket.data.TeamEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface TeamService {

    @GET("teams/")
    suspend fun getTeams(): PaginatedDataEntity<List<TeamEntity>>

    @GET("teams/{id}")
    suspend fun getTeam(@Path("id") teamId: Int): TeamEntity
}