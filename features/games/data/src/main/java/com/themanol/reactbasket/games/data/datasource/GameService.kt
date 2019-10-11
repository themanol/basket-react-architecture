package com.themanol.reactbasket.games.data.datasource

import com.themanol.reactbasket.data.PaginatedDataEntity
import com.themanol.reactbasket.data.GameEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GameService {

    @GET("games?seasons[]=2018")
    fun getGames(@Query("page") page: Int): Single<PaginatedDataEntity<List<GameEntity>>>

    @GET("games?seasons[]=2018")
    fun getGamesByTeam(
        @Query(
            "team_ids[]",
            encoded = true
        ) teamId: Int
    ): Single<PaginatedDataEntity<List<GameEntity>>>

    @GET("games/{id}")
    fun getGame(@Path("id") teamId: Int): Single<GameEntity>
}