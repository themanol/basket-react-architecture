package com.themanol.reactbasket.games.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.themanol.reactbasket.domain.Game
import com.themanol.reactbasket.games.domain.repository.GamesRepository
import com.themanol.reactbasket.viewmodels.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class GamesViewModel(val teamId: Int, val repo: GamesRepository) : BaseViewModel() {

    val gameListLiveData = MutableLiveData<List<Game>>()

    init {
        if(teamId != -1){
            repo.fetchGamesByTeam(teamId)
            repo.gamesByTeamObservable
                .subscribeOn(Schedulers.io())
                .share()

        } else{
            repo.gamesObservable
                .subscribeOn(Schedulers.io())
                .share()

        }.apply {
                subscribe(gameListLiveData::postValue)
                .addTo(disposables)
        }
    }

}