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
    private val gamesByTeamSubject = BehaviorSubject.create<List<Game>>()
    private val gameDetailsSubject = BehaviorSubject.create<Game>()

    override val gamesObservable: Observable<List<Game>>
        get() = gamesSubject.hide()
    override val gamesByTeamObservable: Observable<List<Game>>
        get() = gamesByTeamSubject.hide()
    override val gameDetailsObservable: Observable<Game>
        get() = gameDetailsSubject.hide()


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

    override fun fetchGamesByTeam(id: Int) {
        remoteDataSource
            .getTeamGames(id)
            .map { it.data }
            .flattenAsObservable { it }
            .map { it.toDomain() }
            .toList()
            .subscribeOn(Schedulers.io())
            .subscribe(gamesByTeamSubject::onNext)
            .addTo(disposables)
    }

    override fun fetchGameById(id: Int) {
       remoteDataSource.get(id)
           .map { it.toDomain() }
           .subscribeOn(Schedulers.io())
           .subscribe(gameDetailsSubject::onNext)
           .addTo(disposables)
    }

}