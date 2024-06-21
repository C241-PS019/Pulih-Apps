package com.capstone.pulih.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class HomeKonselorViewModel(application: Application) : AndroidViewModel(application) {
    private var _name = MutableLiveData<String>()
    val name : LiveData<String> get() = _name
    fun getNickName(nama: String){
        _name.value = nama
    }
}