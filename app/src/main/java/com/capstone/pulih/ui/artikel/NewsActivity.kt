package com.capstone.pulih.ui.artikel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pulih.data.response.NewsResponse
import com.capstone.pulih.data.service.ApiService
import com.capstone.pulih.databinding.ActivityNewsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.list.layoutManager = LinearLayoutManager(applicationContext)
        getNews()
    }
    private fun getNews(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitBuild = retrofit.create(ApiService::class.java)
        val retrofitData = retrofitBuild.getNews()
        retrofitData.enqueue(object : Callback<List<NewsResponse>> {
            override fun onResponse(
                p0: Call<List<NewsResponse>?>,
                p1: Response<List<NewsResponse>?>
            ) {
                if(p1.isSuccessful){
                    p1.body()?.let {
                        binding.list.adapter = AdapterNews(it)
                    }
                }
            }
            override fun onFailure(p0: Call<List<NewsResponse>?>, p1: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}