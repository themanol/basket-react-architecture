package com.themanol.reactbasket.teams.presentation.navigation

import com.themanol.reactbasket.navigation.AppNavigation
import com.themanol.reactbasket.teams.presentation.R
import com.themanol.reactbasket.teams.presentation.view.TeamDetailsFragment
import com.themanol.reactbasket.teams.presentation.view.TeamsFragment

class TeamsNavigation : AppNavigation {
    override fun getDestinationNode(): AppNavigation.DestinationNode {
        return AppNavigation.DestinationNode(R.id.teamsFragment, TeamsFragment::class.java.name)
    }

    override val graphId: Int
        get() = R.navigation.teams

}

class TeamDetailsNavigation : AppNavigation {
    override val graphId: Int
        get() = R.navigation.teams

    override fun getDestinationNode(): AppNavigation.DestinationNode {
        return AppNavigation.DestinationNode(R.id.teamDetailsFragment, TeamDetailsFragment::class.java.name)
    }
}