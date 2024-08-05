package com.example.yummy.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI

import com.example.yummy.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
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