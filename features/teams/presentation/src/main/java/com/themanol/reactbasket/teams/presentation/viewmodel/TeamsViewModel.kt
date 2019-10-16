package com.themanol.reactbasket.teams.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.themanol.reactbasket.domain.ResultState
import com.themanol.reactbasket.domain.Team
import com.themanol.reactbasket.teams.domain.repository.TeamRepository
import com.themanol.reactbasket.viewmodels.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class TeamsViewModel(val repo: TeamRepository) : BaseViewModel() {

    private val _teamListLiveData = MutableLiveData<List<Team>>()
    val teamListLiveData: LiveData<List<Team>> = _teamListLiveData
    private val _progressLiveData = MutableLiveData<Boolean>()
    val progressLiveData: LiveData<Boolean> = _progressLiveData

    init {
        val teamObservable = repo.teamsObservable
            .subscribeOn(Schedulers.io())
            .share()

        teamObservable
            .subscribe { result ->
                when (result.status) {
                    ResultState.SUCCESS -> _teamListLiveData.postValue(result.data)
                    ResultState.ERROR -> mErrorLiveData.postValue(result.error)
                    else -> {
                        //Do Nothing
                    }
                }
                _progressLiveData.postValue(result.status == ResultState.IN_PROGRESS)
            }
            .addTo(disposables)
    }
}
