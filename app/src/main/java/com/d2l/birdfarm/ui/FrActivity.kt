package com.d2l.birdfarm.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.d2l.birdfarm.R
import com.d2l.birdfarm.databinding.ActivityFrBinding
import com.d2l.birdfarm.utils.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class FrActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFrBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityFrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView:BottomNavigationView = binding.botNav
        val navController = findNavController(R.id.navhost)
        navView.setupWithNavController(navController)
    }

}