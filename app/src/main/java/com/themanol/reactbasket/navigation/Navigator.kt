package com.themanol.reactbasket.navigation

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.get
import kotlin.collections.set

object Navigator {

    var currentNavController: LiveData<NavController>? = null

    private val navigationMap = HashMap<String, AppNavigation>()


    fun getBottomBarGraphList(): List<Int> {
        val teamsNavId = getNavigation(TeamsRoute).graphId
        val gamesNavId = getNavigation(GamesRoute).graphId
        return listOf(teamsNavId, gamesNavId)
    }

    fun navigateTo(
        route: NavigationRoute,
        bundle: Bundle = Bundle.EMPTY
    ) {
        currentNavController?.value?.let { controller ->
            val navigation =  getNavigation(route)
            val newGraph = controller.navInflater.inflate(
                navigation.graphId
            )
            controller.graph.addDestination(newGraph)
            navigation.getNodeId()?.let {
                val destination = newGraph[it]
                newGraph.remove(destination)
                controller.graph.addDestination(destination)
                controller.navigate(destination.id, bundle)
            } ?: kotlin.run {
                controller.graph.addDestination(newGraph)
                controller.navigate(newGraph.id, bundle)
            }

        }
    }

    private fun getNavigation(navigationRoute: NavigationRoute): AppNavigation {
        return navigationMap[navigationRoute.id]?.let {
            return it
        } ?: run {
            val navigation = AppNavigation.getNavigation(navigationRoute.id)
            navigationMap[navigationRoute.id] = navigation
            return navigation
        }

    }
}

