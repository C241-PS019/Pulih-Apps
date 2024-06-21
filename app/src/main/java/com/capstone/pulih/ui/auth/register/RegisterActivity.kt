package com.capstone.pulih.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.pulih.databinding.ActivityRegisterBinding
import com.capstone.pulih.ui.auth.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var intent : Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.linkLogin.setOnClickListener {
            nextActivity()
        }
        binding.btnRegister.setOnClickListener {
            register()
        }
    }
    private fun register(){
//        Mengambil String dari Input
        val email = binding.edRegisterEmail.text.toString()
        val password = binding.edRegisterPassword.text.toString()
//            Pengkondisian Inputan
        if (email.isNotEmpty()&&password.isNotEmpty()){
//                Firebase Sign In
            firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                if(it.isSuccessful){
                    Toast.makeText(this, "Register Berhasil!", Toast.LENGTH_LONG).show()
                    intent = Intent(this@RegisterActivity,LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this,it.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }
        } else {
            Toast.makeText(this,"Harap isi dengan benar!", Toast.LENGTH_LONG).show()
        }
    }
    private fun nextActivity(){
        intent = Intent(this@RegisterActivity,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}