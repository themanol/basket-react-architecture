package com.themanol.reactbasket.teams.data.datasource

import com.themanol.reactbasket.data.PaginatedDataEntity
import com.themanol.reactbasket.data.PlayerEntity
import io.reactivex.Single

interface PlayerRemoteDataSource {

    fun get(): Single<PaginatedDataEntity<List<PlayerEntity>>>

    fun get(playerId: Int): Single<PlayerEntity>

}
