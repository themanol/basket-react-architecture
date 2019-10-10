package com.themanol.reactbasket.games.presentation.navigation

import com.themanol.reactbasket.games.presentation.R
import com.themanol.reactbasket.games.presentation.view.GamesFragment
import com.themanol.reactbasket.navigation.AppNavigation

class GamesNavigation : AppNavigation {
    override fun getDestinationNode(): AppNavigation.DestinationNode {
        return AppNavigation.DestinationNode(R.id.gamesFragment, GamesFragment::class.java.name)
    }

    override val graphId: Int
        get() = R.navigation.games

}