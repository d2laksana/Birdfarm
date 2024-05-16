package com.d2l.birdfarm.data.api.methods

import com.d2l.birdfarm.data.api.ApiClient
import com.d2l.birdfarm.data.api.requests.auth.LoginRequest
import com.d2l.birdfarm.data.api.requests.auth.RegisterRequest
import com.d2l.birdfarm.data.api.response.auth.LoginResponse
import com.d2l.birdfarm.data.api.response.auth.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("auth/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    companion object {
        fun getApi(): AuthApi? {
            return ApiClient.client?.create(AuthApi::class.java)
        }

    }

}