package com.themanol.reactbasket.views

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.example.android.navigationadvancedsample.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.themanol.reactbasket.R
import com.themanol.reactbasket.navigation.Navigator

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

        val bottomBar: BottomNavigationView = findViewById(R.id.bottom_navigation_bar)

        val controller = bottomBar.setupWithNavController(
            navGraphIds = Navigator.getBottomBarGraphList(),
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )
        controller.observe(this, Observer { navController ->
            Log.d("Themanol", "change controller")
        })
        Navigator.currentNavController = controller

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }
}