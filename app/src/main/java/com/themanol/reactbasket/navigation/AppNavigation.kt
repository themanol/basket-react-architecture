package com.themanol.reactbasket.navigation

interface AppNavigation {

    companion object {
        fun getNavigation(navigationKey: String): AppNavigation =
            Class.forName(navigationKey).newInstance() as AppNavigation
    }

    val graphId: Int
    fun getDestinationNode(): DestinationNode

    data class DestinationNode(val id: Int, val className: String)
}

sealed class NavigationRoute(val id: String)

object TeamsRoute :
    NavigationRoute("com.themanol.reactbasket.teams.presentation.navigation.TeamsNavigation")

object TeamDetailsRoute :
    NavigationRoute("com.themanol.reactbasket.teams.presentation.navigation.TeamDetailsNavigation")

object GamesRoute :
    NavigationRoute("com.themanol.reactbasket.games.presentation.navigation.GamesNavigation")

