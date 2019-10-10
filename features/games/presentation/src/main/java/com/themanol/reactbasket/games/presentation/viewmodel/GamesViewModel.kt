package com.themanol.reactbasket.games.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.themanol.reactbasket.domain.Game
import com.themanol.reactbasket.domain.ResultState
import com.themanol.reactbasket.games.domain.repository.GamesRepository
import com.themanol.reactbasket.viewmodels.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class GamesViewModel(val teamId: Int, val repo: GamesRepository) : BaseViewModel() {

    private val _gameListLiveData = MutableLiveData<List<Game>>()
    val gameListLiveData = _gameListLiveData
    private val _progressLiveData = MutableLiveData<Boolean>()
    val progressLiveData: LiveData<Boolean> = _progressLiveData

    init {
        if (teamId != -1) {
            repo.gamesByTeamObservable
                .subscribeOn(Schedulers.io())
                .share()

        } else {
            repo.gamesObservable
                .subscribeOn(Schedulers.io())
                .share()

        }.apply {
            subscribe(
                { result ->
                    if (result.status == ResultState.SUCCESS) {
                        _gameListLiveData.postValue(result.data)
                    }
                    _progressLiveData.postValue(result.status == ResultState.IN_PROGRESS)
                },
                mErrorLiveData::postValue
            )
                .addTo(disposables)
        }
    }

    fun onStart() {
        if (teamId != -1) {
            repo.fetchGamesByTeam(teamId)
        }
    }

}