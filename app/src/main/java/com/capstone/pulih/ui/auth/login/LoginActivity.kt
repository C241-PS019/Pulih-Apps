package com.capstone.pulih.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.pulih.databinding.ActivityLoginBinding
import com.capstone.pulih.ui.auth.register.RegisterActivity
import com.capstone.pulih.ui.inputdata.InputDataActivity
import com.capstone.pulih.utils.Preferences
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var intent : Intent
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.linkRegister.setOnClickListener {
            nextActivity()
        }
        binding.btnLogin.setOnClickListener {
            login()
        }
    }

    private fun nextActivity(){
        intent = Intent(this@LoginActivity,RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun login() {
//        Mengambil String dari Input
        val email = binding.edLoginEmail.text.toString()
        val password = binding.edLoginPassword.text.toString()
//        Pengkondisian Inputan
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
                    intent = Intent(this@LoginActivity, InputDataActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }
            Preferences().saveRole("USER",applicationContext)
        } else {
            Toast.makeText(this,"Harap isi dengan benar!", Toast.LENGTH_LONG).show()
        }
    }
}