package com.capstone.pulih.ui.mainkonselor.konseling

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pulih.data.response.AjuanResponse
import com.capstone.pulih.data.response.PengajuanResponse
import com.capstone.pulih.data.service.ApiConfig
import com.capstone.pulih.databinding.ActivityWorkspaceBinding
import com.capstone.pulih.ui.mainkonselor.adapter.ListAjuanAdapter
import com.capstone.pulih.utils.Preferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkspaceActivity : AppCompatActivity() {
    private lateinit var listAjuanAdapter: ListAjuanAdapter
    private lateinit var binding: ActivityWorkspaceBinding
    private var pengajuanEntries: List<PengajuanResponse> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityWorkspaceBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val nama = Preferences().getNama(this)
        Log.d("WorkspaceActivity", "Counselor's name: $nama")

        binding.listAjuanUser.layoutManager = LinearLayoutManager(this)
        listAjuanAdapter = ListAjuanAdapter(pengajuanEntries)
        binding.listAjuanUser.adapter = listAjuanAdapter

        getPengajuanEntries()

    }

    private fun getPengajuanEntries() {
        val retrofit = ApiConfig.getApiService(this)
        val call = retrofit.getAjuan()
        call.enqueue(object : Callback<List<PengajuanResponse>> {
            override fun onResponse(call: Call<List<PengajuanResponse>>, response: Response<List<PengajuanResponse>>) {
                Log.d("WorkspaceActivity", "Got response: $response")
                if (response.isSuccessful) {
                    response.body()?.let { entries ->
                        pengajuanEntries = entries
                        Log.d("WorkspaceActivity", "Got Pengajuan Entries: $pengajuanEntries")
                        listAjuanAdapter.updateEntries(pengajuanEntries)
                    }
                }
            }

            override fun onFailure(call: Call<List<PengajuanResponse>>, t: Throwable) {
                Log.e("WorkspaceActivity", "Error: ${t.message}")
            }
        })
    }
}
