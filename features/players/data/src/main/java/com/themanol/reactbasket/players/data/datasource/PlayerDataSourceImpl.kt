package com.themanol.reactbasket.teams.data.datasource

import com.themanol.reactbasket.data.PaginatedDataEntity
import com.themanol.reactbasket.data.PlayerEntity
import com.themanol.reactbasket.games.data.datasource.PlayerService
import io.reactivex.Single

class PlayerDataSourceImpl(private val service: PlayerService) : PlayerRemoteDataSource {

    override fun get(): Single<PaginatedDataEntity<List<PlayerEntity>>> =
        service.getPlayers()

    override fun get(playerId: Int): Single<PlayerEntity> =
        service.getPlayer(playerId)

}
