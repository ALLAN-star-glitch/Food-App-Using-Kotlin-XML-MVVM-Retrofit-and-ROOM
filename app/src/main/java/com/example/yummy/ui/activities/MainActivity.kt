package com.example.yummy.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI

import com.example.yummy.R
import com.example.yummy.mvvm.viewmodelfactories.GeneralViewModelFactory
import com.example.yummy.mvvm.viewmodels.GeneralViewModel
import com.example.yummy.ui.data.db.MealDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    //defining/initializing the general view model in the main activity
    val generalViewModel: GeneralViewModel by lazy {
        val mealDatabase = MealDatabase.getInstance(this)
        val generalViewModelFactory = GeneralViewModelFactory(mealDatabase)
        ViewModelProvider(this, generalViewModelFactory)[GeneralViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        //inflating navigation controller
        val navController = Navigation.findNavController(this, R.id.frag_host)

        //Setting up the navigation controller with the bottom navigation
        NavigationUI.setupWithNavController(bottomNavigation,navController)


    }
}