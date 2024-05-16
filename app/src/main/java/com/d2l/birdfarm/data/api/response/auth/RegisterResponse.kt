package com.d2l.birdfarm.data.api.response.auth

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: DataRegister? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataRegister(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("apikey")
	val apikey: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
