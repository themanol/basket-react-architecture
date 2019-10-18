package com.themanol.reactbasket.games.data.datasource

import com.themanol.reactbasket.data.PaginatedDataEntity
import com.themanol.reactbasket.data.PlayerEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlayerService {

    @GET("players")
    suspend fun getPlayers(@Query("page") page: Int): PaginatedDataEntity<List<PlayerEntity>>

    @GET("players/{id}")
    suspend fun getPlayer(@Path("id") teamId: Int): PlayerEntity

    @GET("players")
    suspend fun searchPlayers(@Query("search") search: String, @Query("page") page: Int)
            : PaginatedDataEntity<List<PlayerEntity>>
}