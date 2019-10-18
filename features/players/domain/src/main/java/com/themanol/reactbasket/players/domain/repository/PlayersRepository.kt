package com.themanol.reactbasket.players.domain.repository

import com.themanol.reactbasket.domain.Player
import com.themanol.reactbasket.domain.Result
import kotlinx.coroutines.flow.Flow

interface PlayersRepository {

    val playersObservable: Flow<Result<List<Player>>>
    val morePlayersObservable: Flow<Result<List<Player>>>
    val playerDetailsObservable: Flow<Result<Player>>

    fun fetchPlayerById(id: Int)
    fun fetchPlayers()
    fun fetchMorePlayers()
    fun searchPlayers(query: String)
    fun searchPlayersNext(query: String)
}