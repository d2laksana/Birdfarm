package com.d2l.birdfarm.data.api.requests.iot

import com.google.gson.annotations.SerializedName

data class fireRequest(
    @SerializedName("value")
    var value: Int
)
