package com.themanol.reactbasket.games.data.repository

import com.themanol.reactbasket.domain.Game
import com.themanol.reactbasket.domain.Result
import com.themanol.reactbasket.games.domain.repository.GamesRepository
import com.themanol.reactbasket.teams.data.datasource.GameRemoteDataSource
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class GamesRepositoryImpl(private val remoteDataSource: GameRemoteDataSource) : GamesRepository {

    private val disposables = CompositeDisposable()
    private val gamesSubject = BehaviorSubject.create<Result<List<Game>>>()
    private val gamesByTeamSubject = BehaviorSubject.create<Result<List<Game>>>()
    private val gameDetailsSubject = BehaviorSubject.create<Result<Game>>()

    override val gamesObservable: Observable<Result<List<Game>>>
        get() = gamesSubject.hide()
    override val gamesByTeamObservable: Observable<Result<List<Game>>>
        get() = gamesByTeamSubject.hide()
    override val gameDetailsObservable: Observable<Result<Game>>
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
            .map { Result.success(it) }
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _ -> gamesSubject.onNext(Result.loading()) }
            .subscribe(
                gamesSubject::onNext
            ) { error ->
                gamesSubject.onNext(Result.error())
                gamesSubject.onError(error)
            }
            .addTo(disposables)
    }

    override fun fetchGamesByTeam(id: Int) {
        remoteDataSource
            .getTeamGames(id)
            .map { it.data }
            .flattenAsObservable { it }
            .map { it.toDomain() }
            .toList()
            .map { Result.success(it) }
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _ -> gamesByTeamSubject.onNext(Result.loading()) }
            .subscribe(gamesByTeamSubject::onNext)
            { error ->
                gamesByTeamSubject.onNext(Result.error())
                gamesByTeamSubject.onError(error)
            }
            .addTo(disposables)
    }

    override fun fetchGameById(id: Int) {
        remoteDataSource.get(id)
            .map { Result.success(it.toDomain()) }
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _ -> gameDetailsSubject.onNext(Result.loading()) }
            .subscribe(gameDetailsSubject::onNext)
            { error ->
                gameDetailsSubject.onNext(Result.error())
                gameDetailsSubject.onError(error)
            }
            .addTo(disposables)
    }

}