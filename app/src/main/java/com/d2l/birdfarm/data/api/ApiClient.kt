package com.d2l.birdfarm.data.api

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://birdfarm.d2l.my.id/api/"

    fun client(context: Context): Retrofit {
        var mHttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        var mOkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(mHttpLoggingInterceptor)
            .addInterceptor(ApiInterceptor(context))
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(mOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}