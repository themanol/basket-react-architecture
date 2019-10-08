package com.themanol.reactbasket.teams.data.repository

import com.themanol.reactbasket.domain.Team
import com.themanol.reactbasket.teams.data.datasource.TeamRemoteDataSource
import com.themanol.reactbasket.teams.domain.repository.TeamRepository
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class TeamRepositoryImpl(private val remoteDataSource: TeamRemoteDataSource): TeamRepository{

    private val disposables = CompositeDisposable()
    private val teamsSubject = BehaviorSubject.create<List<Team>>()
    private val teamDetailsSubject = BehaviorSubject.create<Team>()
    override val teamsObservable: Observable<List<Team>>
    get() = teamsSubject.hide()
    override val teamDetailsObservable: Observable<Team>
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
            .subscribeOn(Schedulers.io())
            .subscribe(teamsSubject::onNext)
            .addTo(disposables)
    }

    override fun fetchTeam(id:Int){
        remoteDataSource
            .get(id)
            .map { it.toDomain() }
            .subscribeOn(Schedulers.io())
            .subscribe(teamDetailsSubject::onNext)
            .addTo(disposables)
    }

}