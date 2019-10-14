package com.themanol.reactbasket.players.presentation.navigation

import com.themanol.players.presentation.R
import com.themanol.reactbasket.navigation.AppNavigation
import com.themanol.reactbasket.players.presentation.view.PlayersFragment

class PlayersNavigation : AppNavigation {
    override fun getDestinationNode(): AppNavigation.DestinationNode {
        return AppNavigation.DestinationNode(R.id.playersFragment, PlayersFragment::class.java.name)
    }

    override val graphId: Int
        get() = R.navigation.players

}