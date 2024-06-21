package com.capstone.pulih.data.service

import android.content.Context
import com.capstone.pulih.BuildConfig
import com.capstone.pulih.utils.AuthConstant
import com.capstone.pulih.utils.Preferences
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        private fun getInterceptor(token: String?):OkHttpClient{
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            return if(token.isNullOrEmpty()){
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()
            } else {
                OkHttpClient.Builder()
                    .addInterceptor(ApiClient(token))
                    .addInterceptor(loggingInterceptor)
                    .build()
            }
        }

        fun getApiService(context: Context):ApiService{
            val sharedPref = Preferences().initPref(context,Preferences.LOGIN)
            val token = sharedPref.getString(AuthConstant.TOKEN,"").toString()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getInterceptor(token))
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}