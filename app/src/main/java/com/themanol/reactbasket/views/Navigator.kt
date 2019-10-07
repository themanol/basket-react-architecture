package com.themanol.reactbasket.views

import androidx.lifecycle.LiveData
import androidx.navigation.NavController

object Navigator {

    var currentNavController: LiveData<NavController>? = null

    private val navigationMap = HashMap<String, AppNavigation>()


    fun getBottomBarGraphList(): List<Int> {
        val teamsNavId = getNavigation(TeamsRoute).graphId
        val gamesNavId = getNavigation(GamesRoute).graphId
        return listOf(teamsNavId, gamesNavId)
    }

    fun navigateTo(route: NavigationRoute) {
        currentNavController?.value?.let { controller ->
            val newGraph = controller.navInflater.inflate(getNavigation(route).graphId)
            controller.graph.addDestination(newGraph)
            controller.navigate(newGraph.id)
        }
    }

    private fun getNavigation(navigationRoute: NavigationRoute): AppNavigation {
        return navigationMap[navigationRoute.id]?.let {
            return it
        }?: run{
            val navigation = AppNavigation.getNavigation(navigationRoute.id)
            navigationMap[navigationRoute.id] = navigation
            return navigation
        }

    }


}

