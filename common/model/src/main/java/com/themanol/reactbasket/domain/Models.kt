package com.themanol.reactbasket.domain

import java.util.*

open class BaseDomainEntity

enum class Conference {
    EAST, WEST;

    companion object {
        fun getConferenceByName(name: String) = valueOf(name.toUpperCase(Locale.ROOT))
    }
}

enum class Division {
    ATLANTIC, CENTRAL, SOUTHEAST, NORTHWEST, PACIFIC, SOUTHWEST;

    companion object {
        fun getDivisionByName(name: String) = Division.valueOf(name.toUpperCase(Locale.ROOT))
    }
}

data class Team(
    val id: Int,
    val abbreviation: String,
    val city: String,
    val conference: Conference,
    val division: Division,
    val fullName: String,
    val name: String
) : BaseDomainEntity()

data class Player(
    val id: Int,
    val firstName: String,
    val heightFeet: Int,
    val heightInches: Int,
    val lastName: String,
    val position: String,
    val team: Team,
    val weightPounds: Int
) : BaseDomainEntity()

data class Game(
    val id: Int,
    val date: Date,
    val homeTeam: Team,
    val homeTeamScore: Int,
    val period: Int,
    val postseason: Boolean,
    val season: Int,
    val status: String,
    val time: String,
    val visitorTeam: Team,
    val visitorTeamScore: Int
) : BaseDomainEntity()


