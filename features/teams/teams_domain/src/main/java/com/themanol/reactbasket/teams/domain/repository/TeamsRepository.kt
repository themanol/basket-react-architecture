package com.themanol.reactbasket.teams.domain.repository

import com.themanol.reactbasket.domain.Team
import io.reactivex.Observable

interface TeamRepository {

    val teamsObservable: Observable<List<Team>>
}