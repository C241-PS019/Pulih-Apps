package com.capstone.pulih.data.repository

import com.capstone.pulih.data.service.ApiService

class Repository(private val apiService:ApiService) {
//    fun getDataUser(user_id:String):LiveData<Result<UserLogin>> = liveData{
//        emit(Result.Loading)
//        try {
//            val response = apiService.getPengguna(user_id)
//            val data = response.userLoginResult
//            emit(Result.Success(data))
//        } catch (e: Exception){
//            Log.e("InputDataViewModel", "GetPengguna: ${e.message.toString()}")
//            emit(Result.Error(e.message.toString()))
//        }
//    }

}