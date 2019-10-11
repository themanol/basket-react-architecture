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
    val gameListLiveData: LiveData<List<Game>> = _gameListLiveData
    private val _progressLiveData = MutableLiveData<Boolean>()
    val progressLiveData: LiveData<Boolean> = _progressLiveData
    private val _onScrollEndLiveData = MutableLiveData<() -> Unit>()
    val onScrollEndLiveData: LiveData<() -> Unit> = _onScrollEndLiveData
    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData

    init {
        val gamesObservable = if (teamId != -1) {
            repo.gamesByTeamObservable
                .subscribeOn(Schedulers.io())
                .share()
        } else {
            repo.gamesObservable
                .subscribeOn(Schedulers.io())
                .share()
        }

        gamesObservable.subscribe { result ->
            when (result.status) {
                ResultState.SUCCESS -> _gameListLiveData.postValue(result.data)
                ResultState.ERROR -> mErrorLiveData.postValue(result.error)
                else -> {
                    //Do Nothing
                }
            }
            _progressLiveData.postValue(result.status == ResultState.IN_PROGRESS)
        }
            .addTo(disposables)

        gamesObservable.subscribe {
            _onScrollEndLiveData.postValue {
                if (it.status != ResultState.IN_PROGRESS) {
                    if (teamId != -1) {
                        repo.fetchMoreGamesByTeam(teamId)
                    } else {
                        repo.fetchMoreGames()
                    }
                }
            }
        }.addTo(disposables)

        val moreGamesObservable = if (teamId != -1) {
            repo.moreGamesByTeamObservable
                .subscribeOn(Schedulers.io())
                .share()
        } else {
            repo.moreGamesObservable
                .subscribeOn(Schedulers.io())
                .share()
        }

        moreGamesObservable.subscribe {
            _onScrollEndLiveData.postValue {
                if (it.status != ResultState.IN_PROGRESS) {
                    if (teamId != -1) {
                        repo.fetchMoreGamesByTeam(teamId)
                    } else {
                        repo.fetchMoreGames()
                    }
                }
            }
        }.addTo(disposables)

        moreGamesObservable.subscribe { result ->
            when (result.status) {
                ResultState.SUCCESS -> result.data?.let { newList ->
                    _gameListLiveData.value?.let {
                        _gameListLiveData.postValue(it.plus(newList))
                    }
                }
                ResultState.ERROR -> mErrorLiveData.postValue(result.error)
                else -> {
                    //Do Nothing
                }
            }
            _loadingLiveData.postValue(result.status == ResultState.IN_PROGRESS)
        }.addTo(disposables)
    }

    fun onStart() {
        if (teamId != -1) {
            repo.fetchGamesByTeam(teamId)
        }
    }

}