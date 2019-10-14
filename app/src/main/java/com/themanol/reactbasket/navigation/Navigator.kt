package com.themanol.reactbasket.navigation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigator
import com.themanol.reactbasket.R
import kotlin.collections.set

object Navigator {

    var currentNavController: LiveData<NavController>? = null

    private val navigationMap = HashMap<String, AppNavigation>()


    fun getBottomBarGraphList(): List<Int> {
        val teamsNavId = getNavigation(TeamsRoute).graphId
        val gamesNavId = getNavigation(GamesRoute).graphId
        val playersNavId = getNavigation(PlayersRoute).graphId
        return listOf(teamsNavId, gamesNavId, playersNavId)
    }

    fun navigateTo(
        route: NavigationRoute,
        bundle: Bundle = Bundle.EMPTY
    ) {
        currentNavController?.value?.let { controller ->
            val navigation = getNavigation(route)
            val destinationNode = navigation.getDestinationNode()
            val destination =
                controller.navigatorProvider.getNavigator(FragmentNavigator::class.java)
                    .createDestination().apply {
                        id = destinationNode.id
                        setClassName(destinationNode.className)
                    }
            destination.id = View.generateViewId()
            controller.graph.addDestination(destination)
            val builder = NavOptions.Builder()
                .setEnterAnim(R.anim.nav_default_enter_anim)
                .setExitAnim(R.anim.nav_default_exit_anim)
                .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
            controller.navigate(destination.id, bundle, builder.build())
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

