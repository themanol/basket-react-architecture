package com.themanol.reactbasket.players.data.repository

import com.themanol.reactbasket.data.init
import com.themanol.reactbasket.domain.Pager
import com.themanol.reactbasket.domain.Player
import com.themanol.reactbasket.domain.Result
import com.themanol.reactbasket.players.domain.repository.PlayersRepository
import com.themanol.reactbasket.teams.data.datasource.PlayerRemoteDataSource
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

@FlowPreview
@ExperimentalCoroutinesApi
class PlayersRepositoryImpl(
    private val remoteDataSource: PlayerRemoteDataSource,
    private val pager: Pager,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : PlayersRepository {

    private val playersSubject = ConflatedBroadcastChannel<Result<List<Player>>>()
    private val morePlayersSubject = ConflatedBroadcastChannel<Result<List<Player>>>()
    private val playerDetailsSubject = ConflatedBroadcastChannel<Result<Player>>()

    override val playersObservable: Flow<Result<List<Player>>>
        get() = playersSubject.init { fetchPlayers() }
    override val morePlayersObservable: Flow<Result<List<Player>>>
        get() = morePlayersSubject.asFlow()
    override val playerDetailsObservable: Flow<Result<Player>>
        get() = playerDetailsSubject.asFlow()

    init {
        fetchPlayers()
    }

    override fun fetchPlayers() {
        scope.launch {
            playersSubject.send(Result.loading())
            try {
                val result = pager.start { initialPage ->
                    remoteDataSource.getPlayers(initialPage)
                }
                playersSubject.send(Result.success(result.data.map { it.toDomain() }))
            } catch (e: Exception) {
                playersSubject.send(Result.error(e.message ?: ""))
            }
        }
    }

    override fun fetchPlayerById(id: Int) {
        scope.launch {
            playerDetailsSubject.send(Result.loading())
            try {
                playerDetailsSubject.send(Result.success(remoteDataSource.get(id).toDomain()))
            } catch (e: Exception) {
                playerDetailsSubject.send(Result.error(e.message ?: ""))
            }
        }
    }

    override fun fetchMorePlayers() {
        scope.launch {
            morePlayersSubject.send(Result.loading())
            try {
                val result = pager.more { next ->
                    remoteDataSource.getPlayers(next)
                }
                result?.let {
                    morePlayersSubject.send(Result.success(it.data.map { it.toDomain() }))
                }
            } catch (e: Exception) {
                morePlayersSubject.send(Result.error(e.message ?: ""))
            }
        }
    }

    override fun searchPlayers(query: String) {
        scope.launch {
            playersSubject.send(Result.loading())
            try {
                val result = pager.start { initialPage ->
                    remoteDataSource
                        .searchPlayers(query, initialPage)
                }
                playersSubject.send(Result.success(result.data.map { it.toDomain() }))
            } catch (e: Exception) {
                playersSubject.send(Result.error(e.message ?: ""))
            }
        }
    }

    override fun searchPlayersNext(query: String) {
        scope.launch {
            morePlayersSubject.send(Result.loading())
            try {
                val result = pager.more { next ->
                    remoteDataSource
                        .searchPlayers(query, next)
                }
                result?.let {
                    morePlayersSubject.send(Result.success(it.data.map { it.toDomain() }))
                }
            } catch (e: Exception) {
                morePlayersSubject.send(Result.error(e.message ?: ""))
            }

        }
    }

}