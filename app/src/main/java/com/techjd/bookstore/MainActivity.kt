package com.techjd.bookstore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.techjd.bookstore.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    lateinit var navGraph: NavGraph
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        navHostFragment =
            supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)


        navGraph = navController.navInflater.inflate(R.navigation.main_nav_graph)

        setStartDestinationAsSplashFragment()



    }

    fun setStartDestinationAsHomeFragment() {
        navGraph.setStartDestination(R.id.homeFragment)
        navController.graph = navGraph
    }

    fun setStartDestinationAsSplashFragment() {
        navGraph.setStartDestination(R.id.splashFragment)
        navController.graph = navGraph
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }

}