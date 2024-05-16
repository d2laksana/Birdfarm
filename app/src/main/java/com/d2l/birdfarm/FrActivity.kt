package com.d2l.birdfarm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.d2l.birdfarm.databinding.ActivityFrBinding
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