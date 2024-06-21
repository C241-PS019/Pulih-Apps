package com.capstone.pulih.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.pulih.utils.Injection

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(InputDataViewModel::class.java)->{
                InputDataViewModel(Injection.provideRepository(context)) as T
            }
            else -> throw IllegalArgumentException("Kelas ViewModel Tidak Diketahui")
        }
    }
}