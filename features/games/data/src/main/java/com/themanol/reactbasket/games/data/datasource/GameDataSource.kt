package com.themanol.reactbasket.teams.data.datasource

import com.themanol.reactbasket.data.GameEntity
import com.themanol.reactbasket.data.PaginatedDataEntity

interface GameRemoteDataSource {

    suspend fun getGames(page: Int): PaginatedDataEntity<List<GameEntity>>

    suspend fun getGame(gameId: Int): GameEntity

    suspend fun getTeamGames(teamId: Int, page: Int): PaginatedDataEntity<List<GameEntity>>
}
