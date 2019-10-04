package com.themanol.reactbasket.teams.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph
import com.themanol.reactbasket.teams.presentation.R
import com.themanol.reactbasket.views.AppNavigation

class TeamsNavigation : AppNavigation{
    override fun addNavigationGraph(navigationController: NavController): NavGraph {
        val destinationGraph = navigationController.navInflater.inflate(R.navigation.teams_navigation_graph)
        destinationGraph.startDestination
        // Dynamically add the destination target to our primary graph
        navigationController.graph.addDestination(destinationGraph)
        return destinationGraph
    }

}