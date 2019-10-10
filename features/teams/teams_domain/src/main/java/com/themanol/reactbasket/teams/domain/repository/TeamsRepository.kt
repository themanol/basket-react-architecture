package com.themanol.reactbasket.teams.domain.repository

import com.themanol.reactbasket.domain.Result
import com.themanol.reactbasket.domain.Team
import io.reactivex.Observable

interface TeamRepository {

    val teamsObservable: Observable<Result<List<Team>>>
    val teamDetailsObservable: Observable<Result<Team>>
    fun fetchTeam(id: Int)
}