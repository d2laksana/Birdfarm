package com.d2l.birdfarm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.d2l.birdfarm.data.api.requests.user.userUpdateRequest
import com.d2l.birdfarm.data.api.response.BaseResponse
import com.d2l.birdfarm.data.api.response.ErrorResponse
import com.d2l.birdfarm.data.api.response.user.UserUpdateResponse
import com.d2l.birdfarm.repository.UserRepository
import com.d2l.birdfarm.utils.SessionManager
import com.google.gson.Gson
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {
    val userRepo = UserRepository()
    val updateResult: MutableLiveData<BaseResponse<UserUpdateResponse>> = MutableLiveData()
    private val initialUpdateResult = BaseResponse.Initial<UserUpdateResponse>()
    private var updateUserJob: Job? = null

    fun updateUser(id: String, name: String, email: String){
        updateResult.value = BaseResponse.Loading()
        updateUserJob = viewModelScope.launch {
            try {
                val userUpdateRequest = userUpdateRequest(
                    name = name,
                    email = email
                )
                val response = userRepo.updateUser(id= id , userUpdateRequest = userUpdateRequest, getApplication<Application>().applicationContext)
                if (response?.code() == 200){
                    updateResult.value = BaseResponse.Success(response.body())
                } else {
                    val erBody = response?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(erBody, ErrorResponse::class.java)
                    updateResult.value = BaseResponse.Error(errorResponse.message)
                }

            } catch (ex: Exception){
                updateResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun resetData(){
        updateResult.value = initialUpdateResult
    }

    override fun onCleared() {
        super.onCleared()
        updateUserJob?.cancel()

        updateResult.value = initialUpdateResult
    }
}