package com.themanol.reactbasket.games.data.repository

import com.themanol.reactbasket.domain.Game
import com.themanol.reactbasket.games.domain.repository.GamesRepository
import com.themanol.reactbasket.teams.data.datasource.GameRemoteDataSource
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class GamesRepositoryImpl(private val remoteDataSource: GameRemoteDataSource): GamesRepository{

    private val disposables = CompositeDisposable()
    private val gamesSubject = BehaviorSubject.create<List<Game>>()
    override val gamesObservable: Observable<List<Game>>
        get() = gamesSubject.hide()

    init {
        fetchGames()
    }

    private fun fetchGames() {
        remoteDataSource
            .get()
            .map { it.data }
            .flattenAsObservable { it }
            .map { it.toDomain() }
            .toList()
            .subscribeOn(Schedulers.io())
            .subscribe(gamesSubject::onNext)
            .addTo(disposables)
    }

}