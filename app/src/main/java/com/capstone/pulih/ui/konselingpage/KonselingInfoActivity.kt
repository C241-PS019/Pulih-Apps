package com.capstone.pulih.ui.konselingpage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.pulih.databinding.ActivityKonselingInfoBinding
import com.capstone.pulih.ui.main.MainActivity

class KonselingInfoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityKonselingInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKonselingInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivBack.setOnClickListener {
            val intent = Intent(this@KonselingInfoActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@KonselingInfoActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}