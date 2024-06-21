package com.capstone.pulih.ui.onboarding.welcome

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.pulih.R
import com.capstone.pulih.databinding.ActivityWelcomeBinding
import com.capstone.pulih.ui.auth.login.LoginActivity
import com.capstone.pulih.ui.auth.loginkonselor.LoginKonselorActivity
import com.capstone.pulih.ui.main.MainActivity
import com.capstone.pulih.ui.mainkonselor.MainKonselorActivity
import com.capstone.pulih.utils.Preferences
import com.google.firebase.auth.FirebaseAuth

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWelcomeBinding
    private  lateinit var intent : Intent
    private var firebaseAuth = FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser!=null&&Preferences().getRole(applicationContext)=="USER"){
            intent = Intent(this@WelcomeActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        } else if (firebaseAuth.currentUser!=null&&Preferences().getRole(applicationContext)=="KONSELOR") {
            intent = Intent(this@WelcomeActivity,MainKonselorActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLoginUser.setOnClickListener {
            intent = Intent(this@WelcomeActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnLoginCounselor.setOnClickListener{
            intent = Intent(this@WelcomeActivity, LoginKonselorActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}