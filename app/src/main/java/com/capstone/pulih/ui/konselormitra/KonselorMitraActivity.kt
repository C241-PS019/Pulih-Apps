package com.capstone.pulih.ui.konselormitra

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.capstone.pulih.data.response.KonselorResponse
import com.capstone.pulih.data.service.ApiConfig
import com.capstone.pulih.databinding.ActivityKonselorMitraBinding
import com.capstone.pulih.ui.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KonselorMitraActivity : AppCompatActivity() {
    private lateinit var binding : ActivityKonselorMitraBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKonselorMitraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.list.layoutManager = LinearLayoutManager(applicationContext)
        getMitra()
    }

    private fun getMitra(){
        val retrofit = ApiConfig.getApiService(applicationContext)
        val retoriftData = retrofit.getKonselor()
        retoriftData.enqueue(object : Callback<List<KonselorResponse>> {
            override fun onResponse(
                p0: Call<List<KonselorResponse>?>,
                p1: Response<List<KonselorResponse>?>
            ) {
                if(p1.isSuccessful){
                    p1.body()?.let {
                        binding.list.adapter = AdapterListKonselor(it)
                    }
                }
            }

            override fun onFailure(p0: Call<List<KonselorResponse>?>, p1: Throwable) {
            }
        })
    }
}