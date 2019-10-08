package com.themanol.reactbasket.teams.presentation.navigation

import com.themanol.reactbasket.navigation.AppNavigation
import com.themanol.reactbasket.teams.presentation.R

class TeamsNavigation : AppNavigation {
    override val graphId: Int
        get() = R.navigation.teams

}

class TeamDetailsNavigation : AppNavigation {
    override val graphId: Int
        get() = R.navigation.teams

    override fun getNodeId(): Int? {
        return R.id.teamDetailsFragment
    }
}