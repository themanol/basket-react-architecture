package com.themanol.reactbasket.teams.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.themanol.reactbasket.domain.Team
import com.themanol.reactbasket.teams.domain.repository.TeamRepository
import com.themanol.reactbasket.viewmodels.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class TeamDetailsViewModel(val id: Int, val repo: TeamRepository) : BaseViewModel() {

    val teamDetailsLiveData = MutableLiveData<Team>()

    init {
        val teamObservable = repo.teamDetailsObservable
            .subscribeOn(Schedulers.io())
            .share()

        teamObservable
            .subscribe(teamDetailsLiveData::postValue)
            .addTo(disposables)
    }

    fun onStart() {
        repo.fetchTeam(id)
    }

}