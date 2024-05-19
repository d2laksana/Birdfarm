package com.d2l.birdfarm.data.api.methods

import android.content.Context
import com.d2l.birdfarm.data.api.ApiClient
import com.d2l.birdfarm.data.api.requests.user.userUpdateRequest
import com.d2l.birdfarm.data.api.response.user.UserUpdateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {
    @PUT("user/{id}")
    suspend fun updateUser(
        @Path("id") id: String,
        @Body userUpdateRequest: userUpdateRequest
    ): Response<UserUpdateResponse>



    companion object {
        fun getApi(context: Context): UserApi? {
            return ApiClient.client(context).create(UserApi::class.java)
        }
    }
}