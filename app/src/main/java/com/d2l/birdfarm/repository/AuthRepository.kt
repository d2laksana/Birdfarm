package com.d2l.birdfarm.repository

import android.content.Context
import com.d2l.birdfarm.data.api.methods.AuthApi
import com.d2l.birdfarm.data.api.requests.auth.LoginRequest
import com.d2l.birdfarm.data.api.requests.auth.RegisterRequest
import com.d2l.birdfarm.data.api.response.auth.LoginResponse
import com.d2l.birdfarm.data.api.response.auth.RegisterResponse
import retrofit2.Response

class AuthRepository {
    suspend fun loginUser(loginRequest: LoginRequest, context: Context) : Response<LoginResponse>? {
        return  AuthApi.getApi(context)?.loginUser(loginRequest = loginRequest)
    }
    suspend fun registerUser(registerRequest: RegisterRequest, context: Context) : Response<RegisterResponse>? {
        return AuthApi.getApi(context)?.registerUser(registerRequest = registerRequest)
    }

}