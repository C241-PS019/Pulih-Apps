package com.capstone.pulih.ui.main.konseling

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.capstone.pulih.data.response.PengajuanResponse
import com.capstone.pulih.data.service.ApiConfig
import com.capstone.pulih.databinding.ActivityDetailPengajuanBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPengajuanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPengajuanBinding

    companion object{
       const val PENGAJUAN = "PENGAJUAN"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailPengajuanBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val pengajuan = intent.getParcelableExtra<PengajuanResponse>(PENGAJUAN)
        pengajuan?.let { displayPengajuanDetails(it) }

    }

    private fun displayPengajuanDetails(pengajuan: PengajuanResponse) {
        binding.username.text = pengajuan.namaPengguna
        binding.spinnerKonselor.text = pengajuan.namaKonselor
        binding.jenisMasalah.text = pengajuan.jenis
        binding.spinnerTempat.text = pengajuan.tempat
        binding.Tanggal.text = pengajuan.tanggal
        binding.Waktu.text = pengajuan.waktu
        binding.Pesan.text = pengajuan.pesan
        binding.spinnerStatus.text = pengajuan.status
        // Set more fields if needed
    }

    private fun updateStatus(id: Int, status: String, pesan:String) {
        val apiService = ApiConfig.getApiService(this)
        val call = apiService.updateStatus(id, status, pesan)
        call.enqueue(object : Callback<PengajuanResponse> {
            override fun onResponse(call: Call<PengajuanResponse>, response: Response<PengajuanResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@DetailPengajuanActivity, "Status updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@DetailPengajuanActivity, "Failed to update status", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PengajuanResponse>, t: Throwable) {
                Toast.makeText(this@DetailPengajuanActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}