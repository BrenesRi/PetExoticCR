package com.example.petfinder.app.view

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petfinder.R
import com.example.petfinder.app.adapter.PetAdapter
import com.example.petfinder.app.viewmodel.PetViewModel
import com.example.petfinder.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.petfinder.app.model.Profile

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    //private val petViewModel: PetViewModel by viewModels()

    // We need an adapter to connect with the recycle view
    //private val adapter = PetAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // With View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_container
        ) as NavHostFragment
        navController = navHostFragment.navController

        // Setup the bottom navigation view with navController
        if(Profile.profileValue == 0) {
            val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
            bottomNavigationView.setupWithNavController(navController)
            binding.bottomNavAdopter.visibility = View.GONE
        } else{
            val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_adopter)
            bottomNavigationView.setupWithNavController(navController)
            binding.bottomNav.visibility = View.GONE
        }

        // Setup the ActionBar with navController and 3 top level destinations
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeScreen, R.id.addScreen, R.id.myPetsScreen, R.id.profileScreen)
        )


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }
}
