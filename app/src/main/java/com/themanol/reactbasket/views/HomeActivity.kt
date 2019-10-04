package com.themanol.reactbasket.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.themanol.reactbasket.R

class HomeActivity : AppCompatActivity() {

    lateinit var teamsNavigation : AppNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        teamsNavigation = AppNavigation.getNavigation(AppNavigation.TEAMS_NAVIGATION)
        val teamsGraph = teamsNavigation.addNavigationGraph(navController)

        val bottomBar: BottomNavigationView = findViewById(R.id.bottom_navigation_bar)
        navController.navigate(teamsGraph.id)
        NavigationUI.setupWithNavController(
            bottomBar,
           navController
        )

//        bottomBar.setOnNavigationItemSelectedListener {
//            when (it.itemId) {
//                R.id.teamsFragment -> {
//                    navController.navigate(teamsGraph.id)
//                    true
//                }
//                else -> false
//            }
//        }
    }
}