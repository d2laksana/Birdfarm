package com.d2l.birdfarm.data.api.requests.user

import com.google.gson.annotations.SerializedName

data class userUpdateRequest(
    @SerializedName("name")
    var name: String,
    @SerializedName("email")
    var  email: String
)