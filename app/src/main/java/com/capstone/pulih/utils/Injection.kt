package com.capstone.pulih.utils

import android.content.Context
import com.capstone.pulih.data.repository.Repository
import com.capstone.pulih.data.service.ApiConfig

object Injection {
    fun provideRepository(context: Context):Repository{
        val apiService = ApiConfig.getApiService(context)
        return Repository(apiService)
    }
}