package com.themanol.reactbasket.games.data.repository

import com.themanol.reactbasket.domain.Game
import com.themanol.reactbasket.domain.Result
import com.themanol.reactbasket.games.domain.repository.GamesRepository
import com.themanol.reactbasket.teams.data.datasource.GameRemoteDataSource
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class GamesRepositoryImpl(private val remoteDataSource: GameRemoteDataSource) : GamesRepository {

    private val disposables = CompositeDisposable()
    private val gamesSubject = BehaviorSubject.create<Result<List<Game>>>()
    private val moreGamesSubject = BehaviorSubject.create<Result<List<Game>>>()
    private val gamesByTeamSubject = BehaviorSubject.create<Result<List<Game>>>()
    private val gameDetailsSubject = BehaviorSubject.create<Result<Game>>()

    override val gamesObservable: Observable<Result<List<Game>>>
        get() = gamesSubject.hide()
    override val gamesByTeamObservable: Observable<Result<List<Game>>>
        get() = gamesByTeamSubject.hide()
    override val gameDetailsObservable: Observable<Result<Game>>
        get() = gameDetailsSubject.hide()
    override val moreGamesObservable: Observable<Result<List<Game>>>
        get() = moreGamesSubject.hide()

    private var currentPage = 1
    private var nextPage: Int? = null
    private var totalPages = 0
    private var lastPageLoaded = 1

    init {
        fetchGames()
    }

    private fun fetchGames() {
        currentPage = 1
        nextPage = null
        lastPageLoaded = 1
        remoteDataSource
            .getGames(currentPage)
            .map {
                currentPage = it.meta.currentPage
                nextPage = it.meta.nextPage
                totalPages = it.meta.totalPages
                it.data
            }
            .flattenAsObservable { it }
            .map { it.toDomain() }
            .toList()
            .map { Result.success(it) }
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _ -> gamesSubject.onNext(Result.loading()) }
            .onErrorResumeNext { error -> Single.just(Result.error(error.message ?: "")) }
            .subscribe(
                gamesSubject::onNext
            )
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
            .onErrorResumeNext { error -> Single.just(Result.error(error.message ?: "")) }
            .subscribe(gamesByTeamSubject::onNext)
            .addTo(disposables)
    }

    override fun fetchGameById(id: Int) {
        remoteDataSource.getGame(id)
            .map { Result.success(it.toDomain()) }
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _ -> gameDetailsSubject.onNext(Result.loading()) }
            .onErrorResumeNext { error -> Single.just(Result.error(error.message ?: "")) }
            .subscribe(gameDetailsSubject::onNext)
            .addTo(disposables)
    }

    override fun fetchMoreGames() {
        nextPage?.let { next ->
            if (next != lastPageLoaded) {
                System.out.println("current page is " + currentPage)
                lastPageLoaded = next
                remoteDataSource
                    .getGames(next)
                    .map {
                        currentPage = it.meta.currentPage
                        nextPage = it.meta.nextPage
                        it.data
                    }
                    .flattenAsObservable { it }
                    .map { it.toDomain() }
                    .toList()
                    .map { Result.success(it) }
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe { _ -> moreGamesSubject.onNext(Result.loading()) }
                    .onErrorResumeNext { error ->
                        lastPageLoaded = currentPage
                        Single.just(Result.error(error.message ?: ""))
                    }
                    .subscribe(
                        moreGamesSubject::onNext
                    )
                    .addTo(disposables)
            }
        }

    }

}