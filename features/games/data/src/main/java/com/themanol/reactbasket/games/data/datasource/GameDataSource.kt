package com.themanol.reactbasket.teams.data.datasource

import com.themanol.reactbasket.data.DataEntity
import com.themanol.reactbasket.data.GameEntity
import io.reactivex.Single

interface GameRemoteDataSource {

    fun getGames(page: Int): Single<DataEntity<List<GameEntity>>>

    fun getGame(gameId: Int): Single<GameEntity>

    fun getTeamGames(teamId: Int): Single<DataEntity<List<GameEntity>>>
}
