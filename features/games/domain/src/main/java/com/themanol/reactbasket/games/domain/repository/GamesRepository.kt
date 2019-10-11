package com.themanol.reactbasket.games.domain.repository

import com.themanol.reactbasket.domain.Game
import com.themanol.reactbasket.domain.Result
import io.reactivex.Observable

interface GamesRepository {

    val gamesObservable: Observable<Result<List<Game>>>
    val moreGamesObservable: Observable<Result<List<Game>>>
    val gamesByTeamObservable: Observable<Result<List<Game>>>
    val gameDetailsObservable: Observable<Result<Game>>

    fun fetchGameById(id: Int)
    fun fetchGamesByTeam(id: Int)
    fun fetchMoreGames()
}