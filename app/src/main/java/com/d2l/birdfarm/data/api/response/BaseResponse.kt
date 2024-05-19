package com.d2l.birdfarm.data.api.response

sealed class BaseResponse<out T> {
    class Initial<T> : BaseResponse<T>()
    data class Success<out T>(val data: T? = null) : BaseResponse<T>()
    data class Loading(val nothing: Nothing? = null) : BaseResponse<Nothing>()
    data class Error(val message: String?) : BaseResponse<Nothing>()
}