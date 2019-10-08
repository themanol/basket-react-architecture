package com.themanol.reactbasket.games.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.themanol.reactbasket.domain.Game
import com.themanol.reactbasket.games.domain.repository.GamesRepository
import com.themanol.reactbasket.viewmodels.BaseViewModel
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class GameDetailsViewModel(val gameId: Int, val repo: GamesRepository) : BaseViewModel() {

    val gameDetailsLiveData = MutableLiveData<Game>()

    init {
        repo.fetchGameById(gameId)
        repo.gameDetailsObservable
            .subscribeOn(Schedulers.io())
            .share()
            .subscribe(gameDetailsLiveData::postValue)
            .addTo(disposables)
    }
}
