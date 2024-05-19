package com.d2l.birdfarm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.d2l.birdfarm.data.api.response.BaseResponse
import com.d2l.birdfarm.data.api.response.ErrorResponse
import com.d2l.birdfarm.data.api.response.iot.DhtResponse
import com.d2l.birdfarm.repository.IotRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch

class IotViewModel(application: Application): AndroidViewModel(application) {
    val iotRepo = IotRepository()
    val dhtResult: MutableLiveData<BaseResponse<DhtResponse>> = MutableLiveData()
    private val initialResult = BaseResponse.Initial<DhtResponse>()


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
}