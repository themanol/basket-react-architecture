package com.themanol.reactbasket.teams.domain.repository

import com.themanol.reactbasket.domain.Result
import com.themanol.reactbasket.domain.Team
import kotlinx.coroutines.flow.Flow

interface TeamRepository {

    val teamsObservable: Flow<Result<List<Team>>>
    val teamDetailsObservable: Flow<Result<Team>>
    fun fetchTeam(id: Int)
}