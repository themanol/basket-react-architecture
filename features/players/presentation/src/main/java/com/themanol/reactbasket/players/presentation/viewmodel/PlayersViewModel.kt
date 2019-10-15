package com.themanol.reactbasket.players.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.themanol.reactbasket.domain.Player
import com.themanol.reactbasket.domain.ResultState
import com.themanol.reactbasket.players.domain.repository.PlayersRepository
import com.themanol.reactbasket.viewmodels.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class PlayersViewModel(val repo: PlayersRepository) : BaseViewModel() {

    private val _playersListLiveData = MutableLiveData<List<Player>>()
    val playersListLiveData: LiveData<List<Player>> = _playersListLiveData
    private val _progressLiveData = MutableLiveData<Boolean>()
    val progressLiveData: LiveData<Boolean> = _progressLiveData
    private val _onScrollEndLiveData = MutableLiveData<(String) -> Unit>()
    val onScrollEndLiveData: LiveData<(String) -> Unit> = _onScrollEndLiveData
    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingMoreLiveData: LiveData<Boolean> = _loadingLiveData
    private val _onQueryChangeLiveData = MutableLiveData<(String) -> Unit>()
    val onQueryChangeLiveData: LiveData<(String) -> Unit> = _onQueryChangeLiveData
    private val _onCloseSearchLiveData = MutableLiveData<() -> Unit>()
    val onCloseSearchLiveData: LiveData<() -> Unit> = _onCloseSearchLiveData

    var lastSearch: String = ""

    init {
        val playersObservable =
            repo.playersObservable
                .subscribeOn(Schedulers.io())
                .share()

        playersObservable.subscribe { result ->
            when (result.status) {
                ResultState.SUCCESS -> _playersListLiveData.postValue(result.data)
                ResultState.ERROR -> mErrorLiveData.postValue(result.error)
                else -> {
                    //Do Nothing
                }
            }
            _progressLiveData.postValue(result.status == ResultState.IN_PROGRESS)
        }
            .addTo(disposables)

        playersObservable.subscribe { res ->
            _onScrollEndLiveData.postValue { query ->
                if (res.status != ResultState.IN_PROGRESS) {
                    if (query.isEmpty()) {
                        repo.fetchMorePlayers()
                    } else {
                        repo.searchPlayersNext(query)
                    }

                }
            }
        }.addTo(disposables)

        playersObservable.subscribe {
            _onQueryChangeLiveData.postValue { query ->
                if (it.status != ResultState.IN_PROGRESS && query.isNotEmpty()) {
                    repo.searchPlayers(query)
                    lastSearch = query
                }
            }
        }.addTo(disposables)

        playersObservable.subscribe {
            _onCloseSearchLiveData.postValue {
                if (it.status != ResultState.IN_PROGRESS) {
                    repo.fetchPlayers()
                    lastSearch = ""
                }
            }
        }.addTo(disposables)


        val morePlayersObservable = repo.morePlayersObservable
            .subscribeOn(Schedulers.io())
            .share()

        morePlayersObservable.subscribe { result ->
            when (result.status) {
                ResultState.SUCCESS -> result.data?.let { newList ->
                    _playersListLiveData.value?.let {
                        _playersListLiveData.postValue(it.plus(newList))
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
}