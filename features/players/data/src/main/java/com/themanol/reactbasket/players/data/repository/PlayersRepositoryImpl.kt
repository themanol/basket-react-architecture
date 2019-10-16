package com.themanol.reactbasket.players.data.repository

import com.themanol.reactbasket.data.init
import com.themanol.reactbasket.domain.Pager
import com.themanol.reactbasket.domain.Player
import com.themanol.reactbasket.domain.Result
import com.themanol.reactbasket.players.domain.repository.PlayersRepository
import com.themanol.reactbasket.teams.data.datasource.PlayerRemoteDataSource
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class PlayersRepositoryImpl(
    private val remoteDataSource: PlayerRemoteDataSource,
    private val pager: Pager
) : PlayersRepository {

    private val disposables = CompositeDisposable()
    private val playersSubject = BehaviorSubject.create<Result<List<Player>>>()
    private val morePlayersSubject = BehaviorSubject.create<Result<List<Player>>>()
    private val playerDetailsSubject = BehaviorSubject.create<Result<Player>>()

    override val playersObservable: Observable<Result<List<Player>>>
        get() = playersSubject.init { fetchPlayers() }
    override val morePlayersObservable: Observable<Result<List<Player>>>
        get() = morePlayersSubject.hide()
    override val playerDetailsObservable: Observable<Result<Player>>
        get() = playerDetailsSubject.hide()

    init {
        fetchPlayers()
    }

    override fun fetchPlayers() {
        pager.start { initialPage ->
            remoteDataSource
                .getPlayers(initialPage)
        }.map { it.data }
            .flattenAsObservable { it }
            .map { it.toDomain() }
            .toList()
            .map { Result.success(it) }
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _ -> playersSubject.onNext(Result.loading()) }
            .onErrorResumeNext { error ->
                Single.just(Result.error(error.message ?: ""))
            }
            .subscribe(
                playersSubject::onNext
            )
            .addTo(disposables)
    }

    override fun fetchPlayerById(id: Int) {
        remoteDataSource.get(id)
            .map { Result.success(it.toDomain()) }
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _ -> playerDetailsSubject.onNext(Result.loading()) }
            .onErrorResumeNext { error ->
                Single.just(Result.error(error.message ?: ""))
            }
            .subscribe(playerDetailsSubject::onNext)
            .addTo(disposables)
    }

    override fun fetchMorePlayers() {
        pager.more { next ->
            remoteDataSource
                .getPlayers(next)
        }?.map { paginated ->
            paginated.data
        }?.flattenAsObservable { it }?.map { it.toDomain() }?.toList()
            ?.map { Result.success(it) }
            ?.subscribeOn(Schedulers.io())
            ?.doOnSubscribe { _ -> morePlayersSubject.onNext(Result.loading()) }
            ?.onErrorResumeNext { error ->
                Single.just(Result.error(error.message ?: ""))
            }?.subscribe(
                morePlayersSubject::onNext
            )?.addTo(disposables)
    }

    override fun searchPlayers(query: String) {
        pager.start { initialPage ->
            remoteDataSource
                .searchPlayers(query, initialPage)
        }.map { it.data }
            .flattenAsObservable { it }
            .map { it.toDomain() }
            .toList()
            .map { Result.success(it) }
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _ -> playersSubject.onNext(Result.loading()) }
            .onErrorResumeNext { error ->
                Single.just(Result.error(error.message ?: ""))
            }
            .subscribe(
                playersSubject::onNext
            )
            .addTo(disposables)
    }

    override fun searchPlayersNext(query: String) {
        pager.more { next ->
            remoteDataSource
                .searchPlayers(query, next)
        }?.map { paginated ->
            paginated.data
        }?.flattenAsObservable { it }?.map { it.toDomain() }?.toList()
            ?.map { Result.success(it) }
            ?.subscribeOn(Schedulers.io())
            ?.doOnSubscribe { _ -> morePlayersSubject.onNext(Result.loading()) }
            ?.onErrorResumeNext { error ->
                Single.just(Result.error(error.message ?: ""))
            }?.subscribe(
                morePlayersSubject::onNext
            )?.addTo(disposables)
    }

}