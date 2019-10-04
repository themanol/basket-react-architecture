package com.themanol.reactbasket.views

import androidx.navigation.NavController
import androidx.navigation.NavGraph

interface AppNavigation {

    companion object {
        const val TEAMS_NAVIGATION =
            "com.themanol.reactbasket.teams.presentation.navigation.TeamsNavigation";

        fun getNavigation(navigationKey: String): AppNavigation =
            Class.forName(TEAMS_NAVIGATION).newInstance() as AppNavigation

    }

    fun addNavigationGraph(navigationController: NavController): NavGraph
}