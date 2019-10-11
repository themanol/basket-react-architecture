package com.themanol.reactbasket.teams.data.repository

import com.themanol.reactbasket.domain.Result
import com.themanol.reactbasket.domain.Team
import com.themanol.reactbasket.teams.data.datasource.TeamRemoteDataSource
import com.themanol.reactbasket.teams.domain.repository.TeamRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class TeamRepositoryImpl(private val remoteDataSource: TeamRemoteDataSource) : TeamRepository {

    private val disposables = CompositeDisposable()
    private val teamsSubject = BehaviorSubject.create<Result<List<Team>>>()
    private val teamDetailsSubject = BehaviorSubject.create<Result<Team>>()
    override val teamsObservable: Observable<Result<List<Team>>>
        get() = teamsSubject.hide()
    override val teamDetailsObservable: Observable<Result<Team>>
        get() = teamDetailsSubject.hide()

    init {
        fetchTeams()
    }

    private fun fetchTeams() {
        remoteDataSource
            .get()
            .map { it.data }
            .flattenAsObservable { it }
            .map { it.toDomain() }
            .toList()
            .map { Result.success(it) }
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _ -> teamsSubject.onNext(Result.loading()) }
            .onErrorResumeNext { error -> Single.just(Result.error(error.message ?: "")) }
            .subscribe(
                teamsSubject::onNext
            ) { error ->
                System.out.println(error.message)
            }
            .addTo(disposables)
    }

    override fun fetchTeam(id: Int) {
        remoteDataSource
            .get(id)
            .map { Result.success(it.toDomain()) }
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { _ -> teamDetailsSubject.onNext(Result.loading()) }
            .onErrorResumeNext { error -> Single.just(Result.error(error.message ?: "")) }
            .subscribe(
                teamDetailsSubject::onNext
            ) { error ->
                System.out.println(error.message)
            }
            .addTo(disposables)
    }

}