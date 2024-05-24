package com.d2l.birdfarm.data.api.methods

import android.content.Context
import com.d2l.birdfarm.data.api.ApiClient
import com.d2l.birdfarm.data.api.requests.iot.fireRequest
import com.d2l.birdfarm.data.api.response.iot.DhtResponse
import com.d2l.birdfarm.data.api.response.iot.FireResponse
import com.d2l.birdfarm.data.api.response.iot.StockResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IotApi {
    @GET("iot/dht")
    suspend fun getDHT(): Response<DhtResponse>

    @GET("fire/relay")
    suspend fun getRelay(): Response<FireResponse>

    @GET("iot/volume")
    suspend fun getStock(): Response<StockResponse>

    @POST("fire/relay")
    suspend fun postRelay(@Body fireRequest: fireRequest): Response<FireResponse>

    @POST("fire/servo")
    suspend fun postServo(@Body fireRequest: fireRequest): Response<FireResponse>



    companion object {
        fun getApi(context: Context): IotApi? {
            return ApiClient.client(context).create(IotApi::class.java)
        }
    }
}