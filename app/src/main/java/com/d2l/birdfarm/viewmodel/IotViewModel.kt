package com.d2l.birdfarm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.d2l.birdfarm.data.api.requests.iot.fireRequest
import com.d2l.birdfarm.data.api.response.BaseResponse
import com.d2l.birdfarm.data.api.response.ErrorResponse
import com.d2l.birdfarm.data.api.response.iot.DhtResponse
import com.d2l.birdfarm.data.api.response.iot.FireResponse
import com.d2l.birdfarm.data.api.response.iot.StockResponse
import com.d2l.birdfarm.repository.IotRepository
import com.google.gson.Gson
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class IotViewModel(application: Application): AndroidViewModel(application) {
    private val iotRepo = IotRepository()
    val dhtResult: MutableLiveData<BaseResponse<DhtResponse>> = MutableLiveData()
    val relayResult: MutableLiveData<BaseResponse<FireResponse>> = MutableLiveData()
    val postRelayResult: MutableLiveData<BaseResponse<FireResponse>> = MutableLiveData()
    val postServoResult: MutableLiveData<BaseResponse<FireResponse>> = MutableLiveData()
    val stockResult: MutableLiveData<BaseResponse<StockResponse>> = MutableLiveData()
    private var postRelayJob: Job? = null
    private var postServoJob: Job? = null


    fun getDHT(){
        dhtResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = iotRepo.getDHT(getApplication<Application>().applicationContext)
                if (response?.code() == 200){
                    dhtResult.value = BaseResponse.Success(response.body())
                } else {
                    val erBody = response?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(erBody, ErrorResponse::class.java)
                    dhtResult.value = BaseResponse.Error(errorResponse.message)
                }
            } catch (ex: Exception){
                dhtResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun getRelay(){
        relayResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = iotRepo.getRelay(getApplication<Application>().applicationContext)
                if (response?.code() == 200){
                    relayResult.value = BaseResponse.Success(response.body())
                } else {
                    val erBody = response?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(erBody, ErrorResponse::class.java)
                    relayResult.value = BaseResponse.Error(errorResponse.message)
                }
            } catch (ex: Exception){
                relayResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun getStock(){
        stockResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = iotRepo.getStock(getApplication<Application>().applicationContext)
                if (response?.code() == 200){
                    stockResult.value = BaseResponse.Success(response.body())
                } else {
                    val erBody = response?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(erBody, ErrorResponse::class.java)
                    stockResult.value = BaseResponse.Error(errorResponse.message)
                }
            } catch (ex: Exception){
                stockResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun postRelay(value: Int) {
        postRelayResult.value = BaseResponse.Loading()
        postRelayJob = viewModelScope.launch {
            try {
                val fireRequest = fireRequest(
                    value = value
                )
                val response = iotRepo.postRelay(fireRequest = fireRequest, context = getApplication<Application>().applicationContext)
                if (response?.code() == 200){
                    postRelayResult.value = BaseResponse.Success(response?.body())
                } else {
                    val erBody = response?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(erBody, ErrorResponse::class.java)
                    postRelayResult.value = BaseResponse.Error(errorResponse.message)
                }
            } catch (ex: Exception){
                postRelayResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun postServo(value: Int) {
        postServoResult.value = BaseResponse.Loading()
        postServoJob = viewModelScope.launch {
            try {
                val fireRequest = fireRequest(
                    value = value
                )
                val response = iotRepo.postServo(fireRequest = fireRequest, context = getApplication<Application>().applicationContext)
                if (response?.code() == 200){
                    postServoResult.value = BaseResponse.Success(response?.body())
                } else {
                    val erBody = response?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(erBody, ErrorResponse::class.java)
                    postServoResult.value = BaseResponse.Error(errorResponse.message)
                }
            } catch (ex: Exception){
                postServoResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun resetData(){
        postRelayResult.value = BaseResponse.Initial()
        stockResult.value = BaseResponse.Initial()
        dhtResult.value = BaseResponse.Initial()
        relayResult.value = BaseResponse.Initial()
        postServoResult.value = BaseResponse.Initial()
    }

    override fun onCleared() {
        super.onCleared()
        postRelayJob?.cancel()
        postServoJob?.cancel()

        postRelayResult.value = BaseResponse.Initial()
        stockResult.value = BaseResponse.Initial()
        dhtResult.value = BaseResponse.Initial()
        relayResult.value = BaseResponse.Initial()
        postServoResult.value = BaseResponse.Initial()
    }
}