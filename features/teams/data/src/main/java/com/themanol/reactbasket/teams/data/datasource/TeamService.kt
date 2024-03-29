package com.themanol.reactbasket.teams.data.datasource

import com.themanol.reactbasket.data.PaginatedDataEntity
import com.themanol.reactbasket.data.TeamEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TeamService {

    @GET("teams/")
    fun getTeams(): Single<PaginatedDataEntity<List<TeamEntity>>>

    @GET("teams/{id}")
    fun getTeam(@Path("id") teamId: Int): Single<TeamEntity>
}