package com.themanol.reactbasket.teams.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.themanol.reactbasket.domain.ResultState
import com.themanol.reactbasket.domain.Team
import com.themanol.reactbasket.teams.domain.repository.TeamRepository
import com.themanol.reactbasket.viewmodels.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TeamDetailsViewModel(val id: Int, val repo: TeamRepository) : BaseViewModel() {

    private val _teamDetailsLiveData = MutableLiveData<Team>()
    val teamDetailsLiveData: LiveData<Team> = _teamDetailsLiveData
    private val _progressLiveData = MutableLiveData<Boolean>()
    val progressLiveData: LiveData<Boolean> = _progressLiveData

    init {
        viewModelScope.launch {
            repo.teamDetailsObservable.collect { result ->
                when (result.status) {
                    ResultState.SUCCESS -> _teamDetailsLiveData.postValue(result.data)
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
        repo.fetchTeam(id)
    }
}
