package com.themanol.reactbasket.teams.data.datasource

import com.themanol.reactbasket.data.GameEntity
import com.themanol.reactbasket.data.PaginatedDataEntity
import io.reactivex.Single

interface GameRemoteDataSource {

    fun getGames(page: Int): Single<PaginatedDataEntity<List<GameEntity>>>

    fun getGame(gameId: Int): Single<GameEntity>

    fun getTeamGames(teamId: Int, page: Int): Single<PaginatedDataEntity<List<GameEntity>>>
}
