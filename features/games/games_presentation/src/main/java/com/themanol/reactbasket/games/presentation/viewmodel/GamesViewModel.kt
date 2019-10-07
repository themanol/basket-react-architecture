package com.themanol.reactbasket.games.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.themanol.reactbasket.domain.Game
import com.themanol.reactbasket.games.domain.repository.GamesRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class GamesViewModel(val repo: GamesRepository): ViewModel() {

    private val disposables = CompositeDisposable()
    val gameListLiveData = MutableLiveData<List<Game>>()

    init {
        val teamObservable = repo.gamesObservable
            .subscribeOn(Schedulers.io())
            .share()

        teamObservable
            .subscribe(gameListLiveData::postValue)
            .addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
    }

}