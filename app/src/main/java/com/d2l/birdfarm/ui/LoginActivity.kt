package com.d2l.birdfarm.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import com.d2l.birdfarm.R
import com.d2l.birdfarm.data.api.response.BaseResponse
import com.d2l.birdfarm.data.api.response.auth.LoginResponse
import com.d2l.birdfarm.databinding.ActivityLoginBinding
import com.d2l.birdfarm.utils.SessionManager
import com.d2l.birdfarm.viewmodel.AuthViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<AuthViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences(this.getString(R.string.app_name), Context.MODE_PRIVATE)
        val darkModeEnabled = sharedPreferences.getBoolean("darkMode", false)
        if (darkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        binding.btnLogin.setOnClickListener{
            login()
        }

        binding.toSignUp.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }


//        login logic
        val token = SessionManager.getToken(this)
        if (!token.isNullOrBlank()) {
            navigateToDash()
        }

        viewModel.loginResult.observe(this) {
            when(it) {
                is BaseResponse.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                }

                is BaseResponse.Success -> {
                    binding.loading.visibility = View.GONE
                    processLogin(it.data)
                }

                is BaseResponse.Error -> {
                    processError(it.message)
                    binding.loading.visibility = View.GONE
                }
                else -> {
                    binding.loading.visibility = View.GONE
                }
            }
        }


    }

    private fun navigateToDash(){
        val intent = Intent(this, FrActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
    }

    private fun login() {
        val email = binding.email.text.toString()
        val pass = binding.password.text.toString()
        viewModel.loginUser(email = email, pass = pass)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun processError(msg: String?) {
        showToast("Error:$msg")
    }

    private fun processLogin(data: LoginResponse?) {
        showToast("Success:" + data?.message)
        if (!data?.data?.token.isNullOrBlank()) {
            data?.data?.token?.let { SessionManager.saveAuthToken(this, it) }
            val name = data?.data?.name ?: ""
            val email = data?.data?.email ?: ""
            SessionManager.saveUserdata(this, name, email)
            navigateToDash()
        }
    }
}