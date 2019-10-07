package com.themanol.reactbasket.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.themanol.reactbasket.R
import com.themanol.reactbasket.extensions.setupWithNavController

class HomeActivity : AppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    private fun setupBottomNavigationBar() {
        val teamsNavigation = AppNavigation.getNavigation(AppNavigation.TEAMS_NAVIGATION)
        val gamesNavigation = AppNavigation.getNavigation(AppNavigation.GAMES_NAVIGATION)

        val teamsNavHost = teamsNavigation.obtainNavHostFragment(
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            fragmentTag = "bottomNavigation#0")
        val gamesNavHost = gamesNavigation.
            obtainNavHostFragment(
                fragmentManager = supportFragmentManager,
                containerId = R.id.nav_host_container,
                fragmentTag = "bottomNavigation#1")
        val navHostList = listOf(teamsNavHost, gamesNavHost)


        val bottomBar: BottomNavigationView = findViewById(R.id.bottom_navigation_bar)

        val controller = bottomBar.setupWithNavController(
            navHostFragmentList = navHostList,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        currentNavController = controller
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }
}