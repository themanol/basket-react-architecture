package com.themanol.reactbasket.games.domain.repository

import com.themanol.reactbasket.domain.Game
import io.reactivex.Observable

interface GamesRepository {

    val gamesObservable: Observable<List<Game>>
    val gamesByTeamObservable: Observable<List<Game>>
    val gameDetailsObservable: Observable<Game>

    fun fetchGamesByTeam(id: Int)
    fun fetchGameById(id: Int)
}