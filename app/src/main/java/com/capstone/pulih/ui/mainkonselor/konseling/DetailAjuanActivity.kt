
package com.capstone.pulih.ui.mainkonselor.konseling

import android.R
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.pulih.data.response.PengajuanResponse
import com.capstone.pulih.data.service.ApiConfig
import com.capstone.pulih.databinding.ActivityDetailAjuanBinding
import com.capstone.pulih.utils.Preferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailAjuanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAjuanBinding
    private var selectedStatus: String? = null
    private var pengajuan: PengajuanResponse? = null

    companion object{
        const val AJUAN = "AJUAN"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailAjuanBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        pengajuan = intent.getParcelableExtra(AJUAN)
        pengajuan?.let { displayPengajuanDetails(it) }


        setupSpinner()
        binding.btnUbahAjuan.setOnClickListener {
            selectedStatus?.let { status ->
                pengajuan?.let { updateStatus(it.konselorId, status, binding.Pesan.text.toString()) }
            }
        }

    }

    private fun displayPengajuanDetails(pengajuan: PengajuanResponse) {
        binding.username.text = Preferences().getNama(applicationContext)
        binding.spinnerKonselor.text = pengajuan.namaKonselor
        binding.jenisMasalah.text = pengajuan.jenis
        binding.spinnerTempat.text = pengajuan.tempat
        binding.Tanggal.text = pengajuan.tanggal
        binding.Waktu.text = pengajuan.waktu
        binding.Pesan.setText(pengajuan.pesan)

    }

    private fun setupSpinner() {
        val statusOptions = arrayOf("diproses", "disetujui", "selesai", "reschedule")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, statusOptions)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerStatusAjuan.adapter = adapter

        binding.spinnerStatusAjuan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedStatus = statusOptions[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    private fun updateStatus(id: Int, status: String, pesan: String) {
        val apiService = ApiConfig.getApiService(this)
        val call = apiService.updateStatus(id, status, pesan)
        call.enqueue(object : Callback<PengajuanResponse> {
            override fun onResponse(call: Call<PengajuanResponse>, response: Response<PengajuanResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@DetailAjuanActivity, "Status updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@DetailAjuanActivity, "Failed to update status", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PengajuanResponse>, t: Throwable) {
                Toast.makeText(this@DetailAjuanActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }



}
