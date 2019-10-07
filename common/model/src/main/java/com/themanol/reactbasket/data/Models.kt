package com.themanol.reactbasket.data

import com.squareup.moshi.Json
import com.themanol.reactbasket.domain.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

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
    @field:Json(name = "home_team") val homeTeam: TeamEntity,
    @field:Json(name = "home_team_score") val homeTeamScore: Int,
    val period: Int,
    val postseason: Boolean,
    val season: Int,
    val status: String,
    val time: String,
    @field:Json(name = "visitor_team") val visitorTeam: TeamEntity,
    @field:Json(name = "visitor_team_score") val visitorTeamScore: Int
) : BaseEntity<Game>() {
    override fun toDomain(): Game {
        return Game(
            id,
            parseDate(date),
            homeTeam.toDomain(),
            homeTeamScore,
            period,
            postseason,
            season,
            status,
            time,
            visitorTeam.toDomain(),
            visitorTeamScore
        )
    }
}

data class DataEntity<T>(
    val data: T
)

private fun parseDate(date: String): Date {
    val pattern = "yyyy-MM-dd HH:mm:ss z"
    try{
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.ROOT)
        return simpleDateFormat.parse(date)
    }catch (e: ParseException){
        val alternativePattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        val simpleDateFormat = SimpleDateFormat(alternativePattern, Locale.ROOT)
        return simpleDateFormat.parse(date)
    }


}


