package com.d2l.birdfarm.data.api.requests.auth

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("name")
    var name: String,
    @SerializedName("email")
    var  email: String,
    @SerializedName("password")
    var password: String,
    @SerializedName("passwordConfirmation")
    var passwordConfirmation: String,
)
