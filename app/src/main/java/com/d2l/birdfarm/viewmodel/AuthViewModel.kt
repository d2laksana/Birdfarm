package com.d2l.birdfarm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.d2l.birdfarm.data.api.requests.auth.LoginRequest
import com.d2l.birdfarm.data.api.requests.auth.RegisterRequest
import com.d2l.birdfarm.data.api.response.BaseResponse
import com.d2l.birdfarm.data.api.response.ErrorResponse
import com.d2l.birdfarm.data.api.response.auth.LoginResponse
import com.d2l.birdfarm.data.api.response.auth.RegisterResponse
import com.d2l.birdfarm.repository.AuthRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch

class AuthViewModel (application: Application) : AndroidViewModel(application) {
    val repo = AuthRepository()
    val loginResult: MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()
    val regisResult: MutableLiveData<BaseResponse<RegisterResponse>> = MutableLiveData()


    fun loginUser(email: String, pass: String) {
        loginResult.value = BaseResponse.Loading()

        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(
                    email = email,
                    password = pass
                )
                val response = repo.loginUser(loginRequest = loginRequest, getApplication<Application>().applicationContext)
                if (response?.code() == 200) {
                    loginResult.value = BaseResponse.Success(response.body())
                } else {
                    val erBody = response?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(erBody, ErrorResponse::class.java)
                    loginResult.value = BaseResponse.Error(errorResponse.message)
                }

            } catch (ex: Exception) {
                loginResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun registerUser(name: String,email: String, pass: String, passcon: String) {
        regisResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val registerRequest = RegisterRequest(
                    name = name,
                    email = email,
                    password = pass,
                    passwordConfirmation = passcon
                )
                val response = repo.registerUser(registerRequest = registerRequest, getApplication<Application>().applicationContext)
                if (response?.code() == 201) {
                    regisResult.value = BaseResponse.Success(response.body())
                } else {
                    val errorBody = response?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    regisResult.value = BaseResponse.Error(errorResponse.message)
                }
            } catch (ex: Exception) {
                regisResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}