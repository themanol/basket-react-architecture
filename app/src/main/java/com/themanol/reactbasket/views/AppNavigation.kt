package com.themanol.reactbasket.views

import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment

interface AppNavigation {

    companion object {
        const val TEAMS_NAVIGATION =
            "com.themanol.reactbasket.teams.presentation.navigation.TeamsNavigation";

        const val GAMES_NAVIGATION =
            "com.themanol.reactbasket.games.presentation.navigation.GamesNavigation";


        fun getNavigation(navigationKey: String): AppNavigation =
            Class.forName(navigationKey).newInstance() as AppNavigation

    }

    fun obtainNavHostFragment(
        fragmentManager: FragmentManager,
        fragmentTag: String,
        containerId: Int
    ): NavHostFragment
}