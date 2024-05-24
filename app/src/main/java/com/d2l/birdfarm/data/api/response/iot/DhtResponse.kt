package com.d2l.birdfarm.data.api.response.iot

import com.google.gson.annotations.SerializedName

data class DhtResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: DataDHT? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class HumidityItem(

	@field:SerializedName("_field")
	val field: String? = null,

	@field:SerializedName("_measurement")
	val measurement: String? = null,

	@field:SerializedName("apikey")
	val apikey: String? = null,

	@field:SerializedName("_value")
	val value: Float?
)

data class TemperatureItem(

	@field:SerializedName("_field")
	val field: String? = null,

	@field:SerializedName("_measurement")
	val measurement: String? = null,

	@field:SerializedName("apikey")
	val apikey: String? = null,

	@field:SerializedName("_value")
	val value: Float?
)

data class DataDHT(

	@field:SerializedName("temperature")
	val temperature: List<TemperatureItem?>? = null,

	@field:SerializedName("humidity")
	val humidity: List<HumidityItem?>? = null
)
