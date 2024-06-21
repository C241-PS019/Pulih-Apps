package com.capstone.pulih.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ProfileViewModel (application: Application) : AndroidViewModel(application) {
    private var _name = MutableLiveData<String>()
    val name : LiveData<String> get() = _name
    fun getName(nama: String){
        _name.value = nama
    }

    private var _univ = MutableLiveData<String>()
    val univ : LiveData<String> get() = _univ
    fun getUniv(univ: String){
        _univ.value = univ
    }
}