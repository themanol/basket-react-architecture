package com.themanol.reactbasket.teams.data.datasource

import com.themanol.reactbasket.data.PaginatedDataEntity
import com.themanol.reactbasket.data.PlayerEntity
import com.themanol.reactbasket.games.data.datasource.PlayerService

class PlayerDataSourceImpl(private val service: PlayerService) : PlayerRemoteDataSource {

    override suspend fun getPlayers(page: Int): PaginatedDataEntity<List<PlayerEntity>> =
        service.getPlayers(page)

    override suspend fun get(playerId: Int): PlayerEntity =
        service.getPlayer(playerId)

    override suspend fun searchPlayers(
        query: String,
        page: Int
    ): PaginatedDataEntity<List<PlayerEntity>> =
        service.searchPlayers(query, page)

}
