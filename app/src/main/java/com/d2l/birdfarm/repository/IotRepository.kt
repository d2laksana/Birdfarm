package com.d2l.birdfarm.repository

import android.content.Context
import com.d2l.birdfarm.data.api.methods.IotApi
import com.d2l.birdfarm.data.api.requests.iot.fireRequest
import com.d2l.birdfarm.data.api.response.iot.DhtResponse
import com.d2l.birdfarm.data.api.response.iot.FireResponse
import com.d2l.birdfarm.data.api.response.iot.StockResponse
import retrofit2.Response

class IotRepository {
    suspend fun getDHT(context: Context): Response<DhtResponse>? {
        return IotApi.getApi(context)?.getDHT()
    }

    suspend fun getRelay(context: Context): Response<FireResponse>? {
        return IotApi.getApi(context)?.getRelay()
    }

    suspend fun getStock(context: Context): Response<StockResponse>? {
        return IotApi.getApi(context)?.getStock()
    }

    suspend fun postRelay(context: Context, fireRequest: fireRequest): Response<FireResponse>? {
        return IotApi.getApi(context)?.postRelay(fireRequest = fireRequest)
    }

    suspend fun postServo(context: Context, fireRequest: fireRequest): Response<FireResponse>? {
        return IotApi.getApi(context)?.postServo(fireRequest = fireRequest)
    }
}