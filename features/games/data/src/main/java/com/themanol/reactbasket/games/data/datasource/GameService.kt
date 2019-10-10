package com.themanol.reactbasket.games.data.datasource

import com.themanol.reactbasket.data.DataEntity
import com.themanol.reactbasket.data.GameEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GameService {

    @GET("games?seasons[]=2018")
    fun getGames(): Single<DataEntity<List<GameEntity>>>

    @GET("games?seasons[]=2018")
    fun getGamesByTeam(@Query("team_ids[]", encoded = true) teamId: Int): Single<DataEntity<List<GameEntity>>>

    @GET("games/{id}")
    fun getGame(@Path("id") teamId: Int): Single<GameEntity>
}