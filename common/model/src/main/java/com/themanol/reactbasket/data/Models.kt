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
    @field:Json(name = "first_name")  val firstName: String,
    @field:Json(name = "height_feet")val heightFeet: Int?,
    @field:Json(name = "height_inches")val heightInches: Int?,
    @field:Json(name = "last_name")val lastName: String,
    val position: String,
    val team: TeamEntity,
    @field:Json(name = "weight_pounds") val weightPounds: Int?
) : BaseEntity<Player>() {
    override fun toDomain(): Player {
        return Player(
            id,
            firstName,
            heightFeet ?: 0,
            heightInches ?: 0,
            lastName,
            position,
            team.toDomain(),
            weightPounds ?: 0
        )
    }
}

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

data class MetaEntity(
    @field:Json(name = "total_pages") val totalPages: Int,
    @field:Json(name = "current_page") val currentPage: Int,
    @field:Json(name = "next_page") val nextPage: Int?,
    @field:Json(name = "pere_page") val perPage: Int,
    @field:Json(name = "total_count") val totalCount: Int
)

data class PaginatedDataEntity<out T>(
    val data: T,
    val meta: MetaEntity
)

private fun parseDate(date: String): Date {
    val pattern = "yyyy-MM-dd HH:mm:ss z"
    try {
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.ROOT)
        return simpleDateFormat.parse(date)
    } catch (e: ParseException) {
        val alternativePattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        val simpleDateFormat = SimpleDateFormat(alternativePattern, Locale.ROOT)
        return simpleDateFormat.parse(date)
    }


}


