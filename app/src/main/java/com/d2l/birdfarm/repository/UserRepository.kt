package com.d2l.birdfarm.repository

import android.content.Context
import com.d2l.birdfarm.data.api.methods.UserApi
import com.d2l.birdfarm.data.api.requests.user.userUpdateRequest
import com.d2l.birdfarm.data.api.response.user.UserUpdateResponse
import retrofit2.Response

class UserRepository {
    suspend fun updateUser(id: String, userUpdateRequest: userUpdateRequest, context: Context): Response<UserUpdateResponse>? {
        return UserApi.getApi(context)?.updateUser(userUpdateRequest = userUpdateRequest, id = id)
    }
}