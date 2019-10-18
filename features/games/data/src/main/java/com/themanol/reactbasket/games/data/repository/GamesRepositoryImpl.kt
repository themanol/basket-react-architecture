package com.themanol.reactbasket.games.data.repository

import com.themanol.reactbasket.data.init
import com.themanol.reactbasket.domain.Game
import com.themanol.reactbasket.domain.Pager
import com.themanol.reactbasket.domain.Result
import com.themanol.reactbasket.games.domain.repository.GamesRepository
import com.themanol.reactbasket.teams.data.datasource.GameRemoteDataSource
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

@FlowPreview
@ExperimentalCoroutinesApi
class GamesRepositoryImpl(
    private val remoteDataSource: GameRemoteDataSource,
    private val pager: Pager,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : GamesRepository {

    private val gamesSubject = ConflatedBroadcastChannel<Result<List<Game>>>()
    private val moreGamesSubject = ConflatedBroadcastChannel<Result<List<Game>>>()
    private val gamesByTeamSubject = ConflatedBroadcastChannel<Result<List<Game>>>()
    private val moreGamesByTeamSubject = ConflatedBroadcastChannel<Result<List<Game>>>()
    private val gameDetailsSubject = ConflatedBroadcastChannel<Result<Game>>()

    override val gamesObservable: Flow<Result<List<Game>>>
        get() = gamesSubject.init { fetchGames() }
    override val gamesByTeamObservable: Flow<Result<List<Game>>>
        get() = gamesByTeamSubject.asFlow()
    override val gameDetailsObservable: Flow<Result<Game>>
        get() = gameDetailsSubject.asFlow()
    override val moreGamesObservable: Flow<Result<List<Game>>>
        get() = moreGamesSubject.asFlow()
    override val moreGamesByTeamObservable: Flow<Result<List<Game>>>
        get() = moreGamesByTeamSubject.asFlow()

    init {
        fetchGames()
    }

    private fun fetchGames() {
        scope.launch {
            gamesSubject.send(Result.loading())
            try {
                val result = pager.start { initialPage ->
                    remoteDataSource
                        .getGames(initialPage)
                }
                gamesSubject.send(Result.success(result.data.map { it.toDomain() }))
            } catch (e: Exception) {
                gamesSubject.send(Result.error(e.message ?: ""))
            }
        }
    }


    override fun fetchMoreGames() {
        scope.launch {
            moreGamesSubject.send(Result.loading())
            try {
                val result = pager.more { next ->
                    remoteDataSource
                        .getGames(next)
                }
                result?.let {
                    moreGamesSubject.send(Result.success(it.data.map { it.toDomain() }))
                }
            } catch (e: Exception) {
                moreGamesSubject.send(Result.error(e.message ?: ""))
            }
        }
    }

    override fun fetchGamesByTeam(id: Int) {
        scope.launch {
            gamesByTeamSubject.send(Result.loading())
            try {
                val result = pager.start { initialPage ->
                    remoteDataSource
                        .getTeamGames(id, initialPage)
                }
                gamesByTeamSubject.send(Result.success(result.data.map { it.toDomain() }))
            } catch (e: Exception) {
                gamesByTeamSubject.send(Result.error(e.message ?: ""))
            }

        }
    }

    override fun fetchMoreGamesByTeam(id: Int) {
        scope.launch {
            moreGamesByTeamSubject.send(Result.loading())
            try {
                val result = pager.more { next ->
                    remoteDataSource
                        .getTeamGames(id, next)
                }
                result?.let {
                    moreGamesByTeamSubject.send(Result.success(it.data.map { it.toDomain() }))
                }
            } catch (e: Exception) {
                moreGamesByTeamSubject.send(Result.error(e.message ?: ""))
            }
        }
    }

    override fun fetchGameById(id: Int) {
        scope.launch {
            gameDetailsSubject.send(Result.loading())
            try {
                gameDetailsSubject.send(Result.success(remoteDataSource.getGame(id).toDomain()))
            } catch (e: Exception) {
                gameDetailsSubject.send(Result.error(e.message ?: ""))
            }
        }
    }
}
