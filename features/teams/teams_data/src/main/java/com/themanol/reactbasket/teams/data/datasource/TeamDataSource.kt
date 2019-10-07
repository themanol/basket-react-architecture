package com.themanol.reactbasket.teams.data.datasource

import com.themanol.reactbasket.data.DataEntity
import com.themanol.reactbasket.data.TeamEntity
import io.reactivex.Single

interface TeamRemoteDataSource {

    fun get(): Single<DataEntity<List<TeamEntity>>>

    fun get(teamId: Int): Single<TeamEntity>
}
