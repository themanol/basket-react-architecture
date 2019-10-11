package com.themanol.reactbasket.teams.data.datasource

import com.themanol.reactbasket.data.PaginatedDataEntity
import com.themanol.reactbasket.data.PlayerEntity
import io.reactivex.Single

interface PlayerRemoteDataSource {

    fun getPlayers(page: Int): Single<PaginatedDataEntity<List<PlayerEntity>>>

    fun get(playerId: Int): Single<PlayerEntity>

}
