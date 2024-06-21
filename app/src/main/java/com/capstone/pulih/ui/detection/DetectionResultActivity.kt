package com.capstone.pulih.ui.detection

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.capstone.pulih.R
import com.capstone.pulih.data.response.DetectResult
import com.capstone.pulih.databinding.ActivityDetectionResultBinding
import com.capstone.pulih.ui.journaling.ui.CreateJournalingEveningActivity
import com.capstone.pulih.ui.main.MainActivity
import com.capstone.pulih.ui.main.konseling.PengajuanActivity
import com.capstone.pulih.ui.mainkonselor.MainKonselorActivity
import com.capstone.pulih.utils.Preferences

class DetectionResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetectionResultBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetectionResultBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val resultData = intent.getStringExtra("resultData")
        if (resultData != null) {
            displayResult(DetectResult(resultData))
        } else {
            binding.resultFeedback.text = "Error: No result data received"
        }

        binding.resultNumber.text = resultData

        binding.btnHome.setOnClickListener {
            if(Preferences().getRole(applicationContext)=="USER") {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, MainKonselorActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.btnJournaling.setOnClickListener {

        }

        binding.btnKonseling.setOnClickListener {
            val i = Intent(this, PengajuanActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun displayResult(result: DetectResult) {
        when (result.predictLevel) {
            "Stres berat" -> {
                binding.resultFeedback.text = "Kamu sangat dianjurkan untuk melakukan konseling"
                binding.btnKonseling.isVisible = true

            }

            "Stres cukup berat" -> {
                binding.resultFeedback.text = "Kamu Dianjurkan untuk konseling"
                binding.btnKonseling.isVisible = true

            }

            "Stres sedang" -> {
                binding.resultFeedback.text = "Yuk coba jurnaling untuk meredakan stressmu :)"
            }

            "Stres ringan" -> {
                binding.resultFeedback.text = "Yuk coba jurnaling untuk meredakan stressmu :)"

            }

            "Normal" -> {
                binding.resultFeedback.isVisible = false
            }

            else -> {
                binding.resultFeedback.text = "Error: Unknown stress level"

            }
        }
    }
}