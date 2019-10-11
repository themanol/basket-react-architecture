package com.themanol.reactbasket.players.domain.repository

import com.themanol.reactbasket.domain.Player
import com.themanol.reactbasket.domain.Result
import io.reactivex.Observable

interface PlayersRepository {

    val playersObservable: Observable<Result<List<Player>>>
    val morePlayersObservable: Observable<Result<List<Player>>>
    val playerDetailsObservable: Observable<Result<Player>>

    fun fetchPlayerById(id: Int)
    fun fetchMoreGames()
}