package com.themanol.reactbasket.data

import com.squareup.moshi.Json
import com.themanol.reactbasket.domain.BaseDomainEntity
import com.themanol.reactbasket.domain.Conference
import com.themanol.reactbasket.domain.Division
import com.themanol.reactbasket.domain.Team

abstract class BaseEntity<T : BaseDomainEntity> {
    abstract fun toDomain(): T
}

data class TeamEntity(
    val id: Int,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    @field:Json(name = "full_name") val fullName: String,
    val name: String
) : BaseEntity<Team>() {
    override fun toDomain(): Team {
        return Team(
            id,
            abbreviation,
            city,
            Conference.getConferenceByName(conference),
            Division.getDivisionByName(division),
            fullName,
            name
        )
    }
}

data class PlayerEntity(
    val id: Int,
    val firstName: String,
    val heightFeet: Int,
    val heightInches: Int,
    val lastName: String,
    val position: String,
    val team: TeamEntity,
    val weightPounds: Int
)

data class GameEntity(
    val id: Int,
    val date: String,
    val homeTeam: TeamEntity,
    val homeTeamScore: Int,
    val period: Int,
    val postSeason: Boolean,
    val season: Int,
    val status: String,
    val time: String,
    val visitorTeam: TeamEntity,
    val visitorTeamScore: Int
)

data class DataEntity<T>(
    val data:T
)


