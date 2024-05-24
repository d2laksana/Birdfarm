package com.d2l.birdfarm.data.api.response.iot

import com.google.gson.annotations.SerializedName

data class FireResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: Int? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
