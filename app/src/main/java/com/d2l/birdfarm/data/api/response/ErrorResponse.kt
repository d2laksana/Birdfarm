package com.d2l.birdfarm.data.api.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
