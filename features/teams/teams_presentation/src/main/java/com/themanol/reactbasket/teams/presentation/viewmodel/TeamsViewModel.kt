package com.themanol.reactbasket.teams.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.themanol.reactbasket.domain.Team
import com.themanol.reactbasket.teams.domain.repository.TeamRepository
import com.themanol.reactbasket.viewmodels.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class TeamsViewModel(val repo: TeamRepository): BaseViewModel() {

    val teamListLiveData = MutableLiveData<List<Team>>()

    init {
        val teamObservable = repo.teamsObservable
            .subscribeOn(Schedulers.io())
            .share()

        teamObservable
            .subscribe(teamListLiveData::postValue)
            .addTo(disposables)
    }

}