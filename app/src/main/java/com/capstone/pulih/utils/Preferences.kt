package com.capstone.pulih.utils

import android.content.Context
import android.content.SharedPreferences

class Preferences{
    companion object {
        const val LOGIN = "Login"
    }
    fun initPref(context : Context, name: String): SharedPreferences {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    private fun editorPref(context : Context, name : String): SharedPreferences.Editor{
        val sharedPref = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        return sharedPref.edit()
    }

    //    Token
    fun saveToken(token: String, context : Context){
        val saveIdToken = editorPref(context, LOGIN)
        saveIdToken.putString(AuthConstant.TOKEN, token)
        saveIdToken.apply()
    }
    fun getToken(context: Context):String?{
        val sharedPref = initPref(context, LOGIN)
        return sharedPref.getString(AuthConstant.TOKEN,"")
    }

    //    Uid
    fun saveUid(uid: String, context : Context){
        val saveUid = editorPref(context, LOGIN)
        saveUid.putString(AuthConstant.UID, uid)
        saveUid.apply()
    }
    fun getUid(context : Context):String?{
        val sharedPref = initPref(context, LOGIN)
        return sharedPref.getString(AuthConstant.UID,"")
    }

    //    Id
    fun saveId(id: Int, context : Context){
        val saveId = editorPref(context, LOGIN)
        saveId.putInt(AuthConstant.ID, id)
        saveId.apply()
    }
    fun getId(context : Context):Int{
        val sharedPref = initPref(context, LOGIN)
        return sharedPref.getInt(AuthConstant.ID,0)
    }

    fun saveKonselorId(konselorId: Int, context : Context){
        val saveKonselorId = editorPref(context, LOGIN)
        saveKonselorId.putInt(AuthConstant.KONSELOR_ID, konselorId)
        saveKonselorId.apply()
    }
    fun getKonselorId(context : Context):Int{
        val sharedPref = initPref(context, LOGIN)
        return sharedPref.getInt(AuthConstant.KONSELOR_ID,0)
    }

    fun saveKonselorName(nama: String, context : Context){
        val saveKonselorId = editorPref(context, LOGIN)
        saveKonselorId.putString(AuthConstant.NAMA_KONSELOR, nama)
        saveKonselorId.apply()
    }
    fun getKonselorName(context : Context): String? {
        val sharedPref = initPref(context, LOGIN)
        return sharedPref.getString(AuthConstant.NAMA_KONSELOR,"")
    }

    //    Nama
    fun saveNama(nama: String, context : Context){
        val saveNama = editorPref(context, LOGIN)
        saveNama.putString(AuthConstant.NAMA, nama)
        saveNama.apply()
    }
    fun getNama(context : Context):String?{
        val sharedPref = initPref(context, LOGIN)
        return sharedPref.getString(AuthConstant.NAMA,"User")
    }

    //    Univ
    fun saveUniv(univ: String, context : Context){
        val saveUniv = editorPref(context, LOGIN)
        saveUniv.putString(AuthConstant.UNIV, univ)
        saveUniv.apply()
    }
    fun getUniv(context : Context):String?{
        val sharedPref = initPref(context, LOGIN)
        return sharedPref.getString(AuthConstant.UNIV,"")
    }

    //    Nick
    fun saveNick(nick: String, context : Context){
        val saveNick = editorPref(context, LOGIN)
        saveNick.putString(AuthConstant.NICK, nick)
        saveNick.apply()
    }
    fun getNick(context : Context):String?{
        val sharedPref = initPref(context, LOGIN)
        return sharedPref.getString(AuthConstant.NICK,"User")
    }

    //    Is From Edit
    fun setFromEdit(data:Boolean, context: Context){
        val saveFromEdit = editorPref(context, LOGIN)
        saveFromEdit.putBoolean(AuthConstant.IS_FROM_EDIT,data)
        saveFromEdit.apply()
    }
    fun getFromEdit(context: Context):Boolean{
        val sharedPref = initPref(context, LOGIN)
        return sharedPref.getBoolean(AuthConstant.IS_FROM_EDIT,false)
    }

    //    OnBoarding
    fun saveOnBoarding(data:Boolean, context: Context){
        val saveOnBoarding = editorPref(context, LOGIN)
        saveOnBoarding.putBoolean(AuthConstant.ONBOARDING,data)
        saveOnBoarding.apply()
    }
    fun getOnBoarding(context: Context):Boolean{
        val sharedPref = initPref(context, LOGIN)
        return sharedPref.getBoolean(AuthConstant.ONBOARDING,false)
    }
    // ROLE
    fun saveRole(data:String, context: Context){
        val saveRole = editorPref(context, LOGIN)
        saveRole.putString(AuthConstant.ROLE,data)
        saveRole.apply()
    }

    fun getRole(context: Context):String?{
        val sharedPref = initPref(context, LOGIN)
        return sharedPref.getString(AuthConstant.ROLE,"")
    }




    //    Clear
    fun clear(context: Context){
        val clearData = editorPref(context, LOGIN)
        clearData.clear()
    }
}
