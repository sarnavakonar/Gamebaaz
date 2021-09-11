package com.example.gamebaaz.view

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import com.example.gamebaaz.R
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private var isAboveHomeScreenInHomeStack = false
    private var isAboveSearchScreenInSearchStack = false
    private var isAboveProfileScreenInProfileStack = false
    private var currentItem = "home"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setup()
    }

    override fun onBackPressed() {
        //val v = navController.currentBackStackEntry?.destination?.id
        if(navController.currentDestination?.id == R.id.homeFragment ||
            navController.currentDestination?.id == R.id.searchGameFragment ||
            navController.currentDestination?.id == R.id.profileFragment){
            finish()
        }
        else{
            super.onBackPressed()
        }
    }

    private fun setup() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController
//        val graphInflater = navHostFragment.navController.navInflater
//        val navGraph = graphInflater.inflate(R.navigation.nav)
//        navGraph.setStartDestination(R.id.homeFragment)
//        navController.graph = navGraph

        bottomNavigation.setupWithNavController(navController)
        bottomNavigation.menu.forEach {
            val view = bottomNavigation.findViewById<View>(it.itemId)
            view.setOnLongClickListener {
                true
            }
        }

        navHostFragment.childFragmentManager.addOnBackStackChangedListener {
            val bs = navHostFragment.childFragmentManager.fragments
            bs.forEach {
                //Log.e("lala", "$it")
            }
        }

        bottomNavigation.setOnNavigationItemSelectedListener {item ->
            when(item.itemId) {
                R.id.homeFragment -> {

                    if(isAboveHomeScreenInHomeStack){
                        navController.popBackStack(R.id.homeFragment, false)
                    }
                    isAboveHomeScreenInHomeStack = navController.currentDestination?.id != R.id.homeFragment

                    isAboveSearchScreenInSearchStack = false
                    isAboveProfileScreenInProfileStack = false
                    currentItem = "home"

                }
                R.id.searchGameFragment -> {

                    if(isAboveSearchScreenInSearchStack){
                        navController.popBackStack(R.id.searchGameFragment, false)
                    }
                    isAboveSearchScreenInSearchStack = navController.currentDestination?.id != R.id.searchGameFragment

                    isAboveHomeScreenInHomeStack = false
                    isAboveProfileScreenInProfileStack = false
                    currentItem = "search"

                }
                R.id.profileFragment -> {

                    if(isAboveProfileScreenInProfileStack){
                        navController.popBackStack(R.id.profileFragment, false)
                    }
                    isAboveProfileScreenInProfileStack = navController.currentDestination?.id != R.id.profileFragment

                    isAboveHomeScreenInHomeStack = false
                    isAboveSearchScreenInSearchStack = false
                    currentItem = "profile"
                }
            }
            onNavDestinationSelected(item, navController)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.gameDetailFragment, R.id.developerFragment -> {
                    when(currentItem){
                        "home" -> isAboveHomeScreenInHomeStack = true
                        "search" -> isAboveSearchScreenInSearchStack = true
                        "profile" -> isAboveProfileScreenInProfileStack = true
                    }
                }
                else -> {
                }
            }
        }
    }

}











