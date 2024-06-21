package com.capstone.pulih.ui.mainkonselor.konselorguide

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.pulih.R
import com.capstone.pulih.databinding.ActivityKonselorGuideBinding
import com.capstone.pulih.databinding.ActivityPulihBinding

class KonselorGuideActivity : AppCompatActivity() {
    private lateinit var binding : ActivityKonselorGuideBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKonselorGuideBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}