package com.themanol.reactbasket.teams.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.themanol.reactbasket.domain.ResultState
import com.themanol.reactbasket.domain.Team
import com.themanol.reactbasket.teams.domain.repository.TeamRepository
import com.themanol.reactbasket.viewmodels.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class TeamDetailsViewModel(val id: Int, val repo: TeamRepository) : BaseViewModel() {

    private val _teamDetailsLiveData = MutableLiveData<Team>()
    val teamDetailsLiveData: LiveData<Team> = _teamDetailsLiveData
    private val _progressLiveData = MutableLiveData<Boolean>()
    val progressLiveData: LiveData<Boolean> = _progressLiveData

    init {
        val teamObservable = repo.teamDetailsObservable
            .subscribeOn(Schedulers.io())
            .share()

        teamObservable
            .subscribe(
                {
                    if (it.status == ResultState.SUCCESS) {
                        _teamDetailsLiveData.postValue(it.data)
                    }
                    _progressLiveData.postValue(it.status == ResultState.IN_PROGRESS)
                },
                mErrorLiveData::postValue
            )
            .addTo(disposables)
    }

    fun onStart() {
        repo.fetchTeam(id)
    }

}
