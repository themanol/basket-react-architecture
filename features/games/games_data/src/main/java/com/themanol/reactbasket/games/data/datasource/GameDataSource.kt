package com.themanol.reactbasket.teams.data.datasource

import com.themanol.reactbasket.data.DataEntity
import com.themanol.reactbasket.data.GameEntity
import io.reactivex.Single

interface GameRemoteDataSource {

    fun get(): Single<DataEntity<List<GameEntity>>>

    fun get(gameId: Int): Single<GameEntity>
}
