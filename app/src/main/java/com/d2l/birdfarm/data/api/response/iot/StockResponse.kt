package com.d2l.birdfarm.data.api.response.iot

import com.google.gson.annotations.SerializedName

data class StockResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("userid")
	val userid: Int? = null,

	@field:SerializedName("value")
	val value: Float?,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
