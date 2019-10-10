package com.themanol.reactbasket.players.data.repository

import com.themanol.reactbasket.domain.Player
import com.themanol.reactbasket.domain.Result
import com.themanol.reactbasket.players.domain.repository.PlayersRepository
import com.themanol.reactbasket.teams.data.datasource.PlayerRemoteDataSource
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class PlayersRepositoryImpl(private val remoteDataSource: PlayerRemoteDataSource) :
    PlayersRepository {

    private val disposables = CompositeDisposable()
    private val playersSubject = BehaviorSubject.create<Result<List<Player>>>()
    private val playerDetailsSubject = BehaviorSubject.create<Result<Player>>()

    override val playersObservable: Observable<Result<List<Player>>>
        get() = playersSubject.hide()
    override val playerDetailsObservable: Observable<Result<Player>>
        get() = playerDetailsSubject.hide()


    init {
        fetchPlayers()
    }

    private fun fetchPlayers() {
        remoteDataSource
            .get()
            .map { it.data }
            .flattenAsObservable { it }
            .map { it.toDomain() }
            .toList()
            .map { Result.success(it) }
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _ -> playersSubject.onNext(Result.loading()) }
            .subscribe(
                playersSubject::onNext
            ) { error ->
                playersSubject.onNext(Result.error())
                playersSubject.onError(error)
            }
            .addTo(disposables)
    }

    override fun fetchPlayerById(id: Int) {
        remoteDataSource.get(id)
            .map { Result.success(it.toDomain()) }
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _ -> playerDetailsSubject.onNext(Result.loading()) }
            .subscribe(playerDetailsSubject::onNext)
            { error ->
                playerDetailsSubject.onNext(Result.error())
                playerDetailsSubject.onError(error)
            }
            .addTo(disposables)
    }

}