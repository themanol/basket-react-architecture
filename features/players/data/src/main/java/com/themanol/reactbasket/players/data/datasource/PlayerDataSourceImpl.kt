package com.themanol.reactbasket.teams.data.datasource

import com.themanol.reactbasket.data.PaginatedDataEntity
import com.themanol.reactbasket.data.PlayerEntity
import com.themanol.reactbasket.games.data.datasource.PlayerService
import io.reactivex.Single

class PlayerDataSourceImpl(private val service: PlayerService) : PlayerRemoteDataSource {

    override fun getPlayers(page: Int): Single<PaginatedDataEntity<List<PlayerEntity>>> =
        service.getPlayers(page)

    override fun get(playerId: Int): Single<PlayerEntity> =
        service.getPlayer(playerId)

}
