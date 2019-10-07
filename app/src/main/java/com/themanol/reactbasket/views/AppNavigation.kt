package com.themanol.reactbasket.views

interface AppNavigation {

    companion object {
        const val TEAMS_NAVIGATION =
            "com.themanol.reactbasket.teams.presentation.navigation.TeamsNavigation";

        const val GAMES_NAVIGATION =
            "com.themanol.reactbasket.games.presentation.navigation.GamesNavigation";


        fun getNavigation(navigationKey: String): AppNavigation =
            Class.forName(navigationKey).newInstance() as AppNavigation

    }

    val graphId: Int
}