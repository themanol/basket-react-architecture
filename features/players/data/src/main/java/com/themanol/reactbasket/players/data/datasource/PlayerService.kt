package com.themanol.reactbasket.games.data.datasource

import com.themanol.reactbasket.data.DataEntity
import com.themanol.reactbasket.data.PlayerEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface PlayerService {

    @GET("players")
    fun getPlayers(): Single<DataEntity<List<PlayerEntity>>>

    @GET("players/{id}")
    fun getPlayer(@Path("id") teamId: Int): Single<PlayerEntity>
}