package com.d2l.birdfarm.data.api.methods

import android.content.Context
import com.d2l.birdfarm.data.api.ApiClient
import com.d2l.birdfarm.data.api.response.iot.DhtResponse
import retrofit2.Response
import retrofit2.http.GET

interface IotApi {
    @GET("iot/dht")
    suspend fun getDHT(): Response<DhtResponse>



    companion object {
        fun getApi(context: Context): IotApi? {
            return ApiClient.client(context).create(IotApi::class.java)
        }
    }
}