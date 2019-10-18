package com.themanol.reactbasket.games.data.datasource

import com.themanol.reactbasket.data.GameEntity
import com.themanol.reactbasket.data.PaginatedDataEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GameService {

    @GET("games?seasons[]=2018")
    suspend fun getGames(@Query("page") page: Int): PaginatedDataEntity<List<GameEntity>>

    @GET("games?seasons[]=2018")
    suspend fun getGamesByTeam(
        @Query("team_ids[]", encoded = true) teamId: Int,
        @Query("page") page: Int
    ): PaginatedDataEntity<List<GameEntity>>

    @GET("games/{id}")
    suspend fun getGame(@Path("id") teamId: Int): GameEntity
}