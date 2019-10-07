package com.themanol.reactbasket.views

interface AppNavigation {

    companion object {
        fun getNavigation(navigationKey: String): AppNavigation =
            Class.forName(navigationKey).newInstance() as AppNavigation
    }

    val graphId: Int
}

sealed class NavigationRoute {
    abstract val id: String
}

object TeamsRoute : NavigationRoute(){
    override val id: String
        get() = "com.themanol.reactbasket.teams.presentation.navigation.TeamsNavigation"

}

object GamesRoute : NavigationRoute(){
    override val id: String
        get() =  "com.themanol.reactbasket.games.presentation.navigation.GamesNavigation"

}