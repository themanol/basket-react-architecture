package com.themanol.reactbasket.games.domain.repository

import com.themanol.reactbasket.domain.Game
import com.themanol.reactbasket.domain.Result
import kotlinx.coroutines.flow.Flow

interface GamesRepository {

    val gamesObservable: Flow<Result<List<Game>>>
    val moreGamesObservable: Flow<Result<List<Game>>>
    val gamesByTeamObservable: Flow<Result<List<Game>>>
    val moreGamesByTeamObservable: Flow<Result<List<Game>>>
    val gameDetailsObservable: Flow<Result<Game>>

    fun fetchGameById(id: Int)
    fun fetchGamesByTeam(id: Int)
    fun fetchMoreGamesByTeam(id: Int)
    fun fetchMoreGames()
}