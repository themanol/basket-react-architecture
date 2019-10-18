package com.themanol.reactbasket.teams.data.datasource

import com.themanol.reactbasket.data.GameEntity
import com.themanol.reactbasket.data.PaginatedDataEntity
import com.themanol.reactbasket.games.data.datasource.GameService

class GameDataSourceImpl(private val service: GameService) : GameRemoteDataSource {

    override suspend fun getGames(page: Int) =
        service.getGames(page)

    override suspend fun getGame(gameId: Int): GameEntity =
        service.getGame(gameId)

    override suspend fun getTeamGames(
        teamId: Int,
        page: Int
    ): PaginatedDataEntity<List<GameEntity>> =
        service.getGamesByTeam(teamId, page)
}
