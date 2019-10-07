package com.themanol.reactbasket.teams.presentation.navigation

import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import com.themanol.reactbasket.teams.presentation.R
import com.themanol.reactbasket.views.AppNavigation

class TeamsNavigation : AppNavigation {
    override fun obtainNavHostFragment(
        fragmentManager: FragmentManager,
        fragmentTag: String,
        containerId: Int
    ): NavHostFragment {
            // If the Nav Host fragment exists, return it
            val existingFragment = fragmentManager.findFragmentByTag(fragmentTag) as NavHostFragment?
            existingFragment?.let { return it }

            // Otherwise, create it and return it.
            val navHostFragment = NavHostFragment.create( R.navigation.teams)
            fragmentManager.beginTransaction()
                .add(containerId, navHostFragment, fragmentTag)
                .commitNow()
            return navHostFragment
    }

}