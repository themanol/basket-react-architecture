package com.themanol.reactbasket.teams.data.datasource

import com.themanol.reactbasket.data.PaginatedDataEntity
import com.themanol.reactbasket.data.PlayerEntity

interface PlayerRemoteDataSource {

    suspend fun getPlayers(page: Int): PaginatedDataEntity<List<PlayerEntity>>

    suspend fun get(playerId: Int): PlayerEntity

    suspend fun searchPlayers(query: String, page: Int): PaginatedDataEntity<List<PlayerEntity>>

}
