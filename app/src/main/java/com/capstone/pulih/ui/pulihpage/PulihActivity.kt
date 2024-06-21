package com.capstone.pulih.ui.pulihpage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.pulih.databinding.ActivityPulihBinding
import com.capstone.pulih.ui.main.MainActivity

class PulihActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPulihBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPulihBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed(){
        super.onBackPressed()
        val intent = Intent(this@PulihActivity,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}