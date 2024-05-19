package com.d2l.birdfarm.data.api

import android.content.Context
import com.d2l.birdfarm.utils.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor(private val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = SessionManager.getToken(context)
        val apikey = SessionManager.getApikey(context)
        val request = if (!token.isNullOrBlank()){
            chain.request().newBuilder()
                .header("Authorization", "Bearer $token")
                .header("x-api-key", apikey.toString())
                .build()
        } else {
            chain.request()
        }
        return chain.proceed(request)
    }
}