package com.themanol.reactbasket.players.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.themanol.reactbasket.domain.Player
import com.themanol.reactbasket.domain.ResultState
import com.themanol.reactbasket.players.domain.repository.PlayersRepository
import com.themanol.reactbasket.viewmodels.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class PlayerDetailsViewModel(val id: Int, val repo: PlayersRepository) : BaseViewModel() {

    private val _playerDetailsLiveData = MutableLiveData<Player>()
    val playerDetailsLiveData: LiveData<Player> = _playerDetailsLiveData
    private val _progressLiveData = MutableLiveData<Boolean>()
    val progressLiveData: LiveData<Boolean> = _progressLiveData

    init {
        val teamObservable = repo.playerDetailsObservable
            .subscribeOn(Schedulers.io())
            .share()

        teamObservable
            .subscribe {
                when (it.status) {
                    ResultState.SUCCESS -> _playerDetailsLiveData.postValue(it.data)
                    ResultState.ERROR -> mErrorLiveData.postValue(it.error)
                    else -> {
                        //Do Nothing
                    }
                }
                _progressLiveData.postValue(it.status == ResultState.IN_PROGRESS)
            }

            .addTo(disposables)
    }

    fun onStart() {
        repo.fetchPlayerById(id)
    }

}
