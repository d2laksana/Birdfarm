package com.d2l.birdfarm

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val toLogin = findViewById<TextView>(R.id.to_login)

        toLogin.setOnClickListener(){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

//    fun toLogin() {
//        val intent = Intent(this, LoginActivity::class.java)
//        startActivity(intent)
//    }
}