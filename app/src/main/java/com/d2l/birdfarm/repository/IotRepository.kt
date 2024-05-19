package com.d2l.birdfarm.repository

import android.content.Context
import com.d2l.birdfarm.data.api.methods.IotApi
import com.d2l.birdfarm.data.api.response.iot.DhtResponse
import retrofit2.Response

class IotRepository {
    suspend fun getDHT(context: Context): Response<DhtResponse>? {
        return IotApi.getApi(context)?.getDHT()
    }
}