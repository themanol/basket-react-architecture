package com.themanol.reactbasket.teams.data.repository

import com.themanol.reactbasket.data.init
import com.themanol.reactbasket.domain.Result
import com.themanol.reactbasket.domain.Team
import com.themanol.reactbasket.teams.data.datasource.TeamRemoteDataSource
import com.themanol.reactbasket.teams.domain.repository.TeamRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

@FlowPreview
@ExperimentalCoroutinesApi
class TeamRepositoryImpl(
    private val remoteDataSource: TeamRemoteDataSource,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : TeamRepository {

    private val teamsChannel = ConflatedBroadcastChannel<Result<List<Team>>>()
    private val teamDetailsChannel = ConflatedBroadcastChannel<Result<Team>>()
    override val teamsObservable: Flow<Result<List<Team>>>
        get() = teamsChannel.init { fetchTeams() }
    override val teamDetailsObservable: Flow<Result<Team>>
        get() = teamDetailsChannel.asFlow()

    init {
        fetchTeams()
    }

    private fun fetchTeams() {
        scope.launch {
            teamsChannel.send(Result.loading())
            try {
                teamsChannel.send(Result.success(remoteDataSource
                    .get().data
                    .map {
                        it.toDomain()
                    }
                ))
            } catch (e: Exception) {
                teamsChannel.send(Result.error(e.message ?: ""))
            }
        }
    }

    override fun fetchTeam(id: Int) {
        scope.launch {
            teamDetailsChannel.send(Result.loading())
            try {
                teamDetailsChannel.send(
                    Result.success(
                        remoteDataSource
                            .get(id).toDomain()
                    )
                )
            } catch (e: Exception) {
                teamDetailsChannel.send(Result.error(e.message ?: ""))
            }
        }
    }

}