package com.capstone.pulih.ui.detection

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.pulih.R
import com.capstone.pulih.data.response.DetectResult
import com.capstone.pulih.data.service.ApiConfig
import com.capstone.pulih.databinding.ActivityDetectionBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetectionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnDetection.setOnClickListener {
            val selectedOptions = listOf(
                binding.rg1.checkedRadioButtonId,
                binding.rg2.checkedRadioButtonId,
                binding.rg3.checkedRadioButtonId,
                binding.rg4.checkedRadioButtonId,
                binding.rg5.checkedRadioButtonId,
                binding.rg6.checkedRadioButtonId,
                binding.rg7.checkedRadioButtonId,
                binding.rg8.checkedRadioButtonId,
                binding.rg9.checkedRadioButtonId,
                binding.rg10.checkedRadioButtonId
            )

            if (selectedOptions.contains(-1)) {
                showErrors(selectedOptions)
                Toast.makeText(this, "Detection failed: Please answer all questions", Toast.LENGTH_SHORT).show()

            } else{
                postAnswers()
            }
        }
    }

    private fun getSelectedOption(radioGroup: RadioGroup): Int {
        return when (radioGroup.checkedRadioButtonId) {
            R.id.rb1a, R.id.rb2a, R.id.rb3a, R.id.rb4a, R.id.rb5a,
            R.id.rb6a, R.id.rb7a, R.id.rb8a, R.id.rb9a, R.id.rb10a -> 0

            R.id.rb1b, R.id.rb2b, R.id.rb3b, R.id.rb4b, R.id.rb5b,
            R.id.rb6b, R.id.rb7b, R.id.rb8b, R.id.rb9b, R.id.rb10b -> 1

            R.id.rb1c, R.id.rb2c, R.id.rb3c, R.id.rb4c, R.id.rb5c,
            R.id.rb6c, R.id.rb7c, R.id.rb8c, R.id.rb9c, R.id.rb10c -> 2

            R.id.rb1d, R.id.rb2d, R.id.rb3d, R.id.rb4d, R.id.rb5d,
            R.id.rb6d, R.id.rb7d, R.id.rb8d, R.id.rb9d, R.id.rb10d -> 3

            R.id.rb1e, R.id.rb2e, R.id.rb3e, R.id.rb4e, R.id.rb5e,
            R.id.rb6e, R.id.rb7e, R.id.rb8e, R.id.rb9e, R.id.rb10e -> 4

            else -> -1
        }
    }

    private fun showErrors(selectedOptions: List<Int>) {
        if (selectedOptions[0] == -1) binding.textViewError1.visibility = View.VISIBLE
        if (selectedOptions[1] == -1) binding.textViewError2.visibility = View.VISIBLE
        if (selectedOptions[2] == -1) binding.textViewError3.visibility = View.VISIBLE
        if (selectedOptions[3] == -1) binding.textViewError4.visibility = View.VISIBLE
        if (selectedOptions[4] == -1) binding.textViewError5.visibility = View.VISIBLE
        if (selectedOptions[5] == -1) binding.textViewError6.visibility = View.VISIBLE
        if (selectedOptions[6] == -1) binding.textViewError7.visibility = View.VISIBLE
        if (selectedOptions[7] == -1) binding.textViewError8.visibility = View.VISIBLE
        if (selectedOptions[8] == -1) binding.textViewError9.visibility = View.VISIBLE
        if (selectedOptions[9] == -1) binding.textViewError10.visibility = View.VISIBLE
    }

    private fun postAnswers() {
        val retrofit = ApiConfig.getApiService(applicationContext)
        val retrofitData = retrofit.postDeteksi(
            p1 = getSelectedOption(binding.rg1),
            p2 = getSelectedOption(binding.rg2),
            p3 = getSelectedOption(binding.rg3),
            p4 = getSelectedOption(binding.rg4),
            p5 = getSelectedOption(binding.rg5),
            p6 = getSelectedOption(binding.rg6),
            p7 = getSelectedOption(binding.rg7),
            p8 = getSelectedOption(binding.rg8),
            p9 = getSelectedOption(binding.rg9),
            p10 = getSelectedOption(binding.rg10)
        )
        retrofitData.enqueue(object : Callback<DetectResult> {
            override fun onResponse(call: Call<DetectResult>, response: Response<DetectResult>) {
                if (response.isSuccessful && response.body() != null) {
                    val detectResult = response.body()
                    val intent = Intent(this@DetectionActivity, DetectionResultActivity::class.java).apply {
                        putExtra("resultData", detectResult?.predictLevel)
                    }
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@DetectionActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DetectResult>, t: Throwable) {
                Toast.makeText(this@DetectionActivity, "Failure: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
