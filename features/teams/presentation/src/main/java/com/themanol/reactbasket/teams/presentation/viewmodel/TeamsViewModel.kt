package com.themanol.reactbasket.teams.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.themanol.reactbasket.domain.Team
import com.themanol.reactbasket.teams.domain.repository.TeamRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class TeamsViewModel(val repo: TeamRepository): ViewModel() {

    private val disposables = CompositeDisposable()
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