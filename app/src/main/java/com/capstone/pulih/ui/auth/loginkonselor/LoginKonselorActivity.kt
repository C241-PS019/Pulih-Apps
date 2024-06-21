package com.capstone.pulih.ui.auth.loginkonselor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.pulih.data.response.KonselorResponse
import com.capstone.pulih.data.service.ApiConfig
import com.capstone.pulih.databinding.ActivityLoginKonselorBinding
import com.capstone.pulih.ui.mainkonselor.MainKonselorActivity
import com.capstone.pulih.utils.Preferences
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginKonselorActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginKonselorBinding
    private lateinit var intent : Intent
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginKonselorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogin.setOnClickListener {
            login()
        }
    }
    private fun login() {
//        Mengambil String dari Input
        val email = binding.edLoginEmail.text.toString()
        val password = binding.edLoginPassword.text.toString()
//            Pengkondisian Inputan
        if (email.isNotEmpty()&&password.isNotEmpty()){
            firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    user?.getIdToken(true)?.addOnSuccessListener { result ->
                        val token = result.token
                        Preferences().saveToken(token.toString(), applicationContext)
                        Log.d("TOKEN", Preferences().getToken(applicationContext).toString())
                    }
                    Preferences().saveUid(user?.uid.toString(), applicationContext)
                    Log.d("UID", Preferences().getUid(applicationContext).toString())
                    Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_LONG).show()
                    Preferences().saveRole("KONSELOR",applicationContext)
                    getUser()
                    intent = Intent(this@LoginKonselorActivity, MainKonselorActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }
        } else {
            Toast.makeText(this,"Harap isi dengan benar!", Toast.LENGTH_LONG).show()
        }
    }
    private fun getUser() {
        val retrofit = ApiConfig.getApiService(applicationContext)
        val retrofitData = retrofit.getKonselor()

        retrofitData.enqueue(object : Callback<List<KonselorResponse>?> {
            override fun onResponse(
                p0: Call<List<KonselorResponse>?>,
                p1: Response<List<KonselorResponse>?>
            ) {
                val responseBody = p1.body()!!
                for (data in responseBody) {
                    data.id?.let { Preferences().saveId(it, applicationContext) }
                }
                Log.d("Input Data", "Id: ${Preferences().getId(applicationContext)}")
            }

            override fun onFailure(p0: Call<List<KonselorResponse>?>, p1: Throwable) {
                Log.e("Input Data Activity", "onFailure ${p1.message}")
            }

        })
    }
}