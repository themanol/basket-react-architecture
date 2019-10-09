package com.themanol.reactbasket.navigation

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
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
            val destinationNode = navigation.getDestinationNode()
            val destination = controller.navigatorProvider.getNavigator(FragmentNavigator::class.java)
                .createDestination().apply {
                    id = destinationNode.id
                    setClassName(destinationNode.className)
                }
            controller.graph.addDestination(
                destination
            )
            controller.navigate(destination.id, bundle)
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

