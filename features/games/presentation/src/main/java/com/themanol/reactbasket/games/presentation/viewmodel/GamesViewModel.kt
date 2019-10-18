package com.themanol.reactbasket.games.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.themanol.reactbasket.domain.Game
import com.themanol.reactbasket.domain.ResultState
import com.themanol.reactbasket.games.domain.repository.GamesRepository
import com.themanol.reactbasket.viewmodels.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GamesViewModel(val teamId: Int, val repo: GamesRepository) : BaseViewModel() {

    private val _gameListLiveData = MutableLiveData<List<Game>>()
    val gameListLiveData: LiveData<List<Game>> = _gameListLiveData
    private val _progressLiveData = MutableLiveData<Boolean>()
    val progressLiveData: LiveData<Boolean> = _progressLiveData
    private val _onScrollEndLiveData = MutableLiveData<() -> Unit>()
    val onScrollEndLiveData: LiveData<() -> Unit> = _onScrollEndLiveData
    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingMoreLiveData: LiveData<Boolean> = _loadingLiveData

    init {
        viewModelScope.launch {
            if (teamId != -1) {
                repo.gamesByTeamObservable
            } else {
                repo.gamesObservable
            }.collect { result ->
                when (result.status) {
                    ResultState.SUCCESS -> _gameListLiveData.postValue(result.data)
                    ResultState.ERROR -> mErrorLiveData.postValue(result.error)
                    else -> {
                        //Do Nothing
                    }
                }
                _progressLiveData.postValue(result.status == ResultState.IN_PROGRESS)

                _onScrollEndLiveData.postValue {
                    if (result.status != ResultState.IN_PROGRESS) {
                        if (teamId != -1) {
                            repo.fetchMoreGamesByTeam(teamId)
                        } else {
                            repo.fetchMoreGames()
                        }
                    }
                }
            }
        }

        viewModelScope.launch {
            if (teamId != -1) {
                repo.moreGamesByTeamObservable
            } else {
                repo.moreGamesObservable
            }.collect { result ->
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
            }
        }

    }

    fun onStart() {
        if (teamId != -1) {
            repo.fetchGamesByTeam(teamId)
        }
    }

}