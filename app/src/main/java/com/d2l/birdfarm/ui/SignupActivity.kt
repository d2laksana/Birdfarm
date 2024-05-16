package com.d2l.birdfarm.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.d2l.birdfarm.R
import com.d2l.birdfarm.data.api.response.BaseResponse
import com.d2l.birdfarm.data.api.response.auth.RegisterResponse
import com.d2l.birdfarm.databinding.ActivitySignupBinding
import com.d2l.birdfarm.viewmodel.AuthViewModel

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val viewModel by viewModels<AuthViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toLogin.setOnClickListener(){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.signup.setOnClickListener {
            signup()
        }

//        sign up logic
        viewModel.regisResult.observe(this) {
            when(it) {
                is BaseResponse.Loading -> {
                    binding.signLoading.visibility = View.VISIBLE
                }
                is BaseResponse.Success -> {
                    binding.signLoading.visibility = View.GONE
                    processRegis(it.data)
                }
                is BaseResponse.Error -> {
                    processError(it.message)
                    binding.signLoading.visibility = View.GONE
                }

                else -> {
                    binding.signLoading.visibility = View.GONE
                }
            }
        }
    }

    private fun signup() {
        val email = binding.signupEmail.text.toString()
        val name = binding.signupName.text.toString()
        val pass = binding.signupPass.text.toString()
        val pascon = binding.passConfirm.text.toString()
        viewModel.registerUser(name = name, email = email, pass = pass, passcon = pascon)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun processError(msg: String?) {
        showToast("Error:$msg")
    }

    private fun processRegis(data: RegisterResponse?) {
        showToast("Success:" + data?.message)
        if (!data?.data?.apikey.isNullOrBlank()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}