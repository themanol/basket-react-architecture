package com.themanol.reactbasket.players.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.themanol.reactbasket.domain.Player
import com.themanol.reactbasket.domain.ResultState
import com.themanol.reactbasket.players.domain.repository.PlayersRepository
import com.themanol.reactbasket.viewmodels.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PlayerDetailsViewModel(val id: Int, val repo: PlayersRepository) : BaseViewModel() {

    private val _playerDetailsLiveData = MutableLiveData<Player>()
    val playerDetailsLiveData: LiveData<Player> = _playerDetailsLiveData
    private val _progressLiveData = MutableLiveData<Boolean>()
    val progressLiveData: LiveData<Boolean> = _progressLiveData

    init {
        viewModelScope.launch {
            repo.playerDetailsObservable
                .collect { result ->
                    when (result.status) {
                        ResultState.SUCCESS -> _playerDetailsLiveData.postValue(result.data)
                        ResultState.ERROR -> mErrorLiveData.postValue(result.error)
                        else -> {
                            //Do Nothing
                        }
                    }
                    _progressLiveData.postValue(result.status == ResultState.IN_PROGRESS)
                }
        }
    }

    fun onStart() {
        repo.fetchPlayerById(id)
    }

}
