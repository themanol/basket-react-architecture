package com.themanol.reactbasket.teams.data.datasource

import com.themanol.reactbasket.data.DataEntity
import com.themanol.reactbasket.data.GameEntity
import com.themanol.reactbasket.games.data.datasource.GameService
import io.reactivex.Single

class GameDataSourceImpl(private val service: GameService) : GameRemoteDataSource {

    override fun getGames(page: Int): Single<DataEntity<List<GameEntity>>> =
        service.getGames(page)

    override fun getGame(gameId: Int): Single<GameEntity> =
       service.getGame(gameId)

    override fun getTeamGames(teamId: Int): Single<DataEntity<List<GameEntity>>> =
        service.getGamesByTeam(teamId)
}
