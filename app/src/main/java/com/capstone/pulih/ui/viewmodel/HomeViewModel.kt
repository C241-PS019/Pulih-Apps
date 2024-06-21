package com.capstone.pulih.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private var _name = MutableLiveData<String>()
    val name : LiveData<String> get() = _name
    fun getNick(nama: String){
        _name.value = nama
    }
}