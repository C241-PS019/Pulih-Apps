package com.capstone.pulih.ui.main.konseling

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pulih.data.response.PengajuanResponse
import com.capstone.pulih.data.response.UserResponse
import com.capstone.pulih.data.service.ApiConfig
import com.capstone.pulih.databinding.FragmentKonselingBinding
import com.capstone.pulih.utils.Preferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KonselingFragment : Fragment() {
    private var _binding: FragmentKonselingBinding? = null
    private val binding get() = _binding!!
    private var pengajuanEntries: List<PengajuanResponse> = emptyList()
    private var konselorMap: Map<Int, PengajuanResponse> = emptyMap()
    private var konselorId: Int = 0
    private var userId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentKonselingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnKonseling.setOnClickListener {
            val intent = Intent(activity, PengajuanActivity::class.java)
            startActivity(intent)
        }

        getIdJournal(Preferences().getUid(requireContext()).toString())
        getPengajuanData()

        userId = Preferences().getId(requireContext())

        if (userId != null && konselorId != null) {

            Preferences().saveKonselorId(konselorId!!, requireContext())

            Log.d("PengajuanActivity", "User ID is $userId or Konselor ID is $konselorId ")
        } else {
            Log.e("PengajuanActivity", "User ID is $userId or Konselor ID is $konselorId ")
        }

        getKonselorList()
        setupAdapter()


        binding.listPengajuan.layoutManager = LinearLayoutManager(requireContext())
        getPengajuanEntries()
    }

    private fun getPengajuanEntries() {
        val retrofit = ApiConfig.getApiService(requireContext())
        val call = retrofit.getPengajuan()
        call.enqueue(object : Callback<List<PengajuanResponse>> {
            override fun onResponse(call: Call<List<PengajuanResponse>>, response: Response<List<PengajuanResponse>>) {
                Log.d("KonselingFragment", "Got response: $response")
                if (response.isSuccessful) {
                    response.body()?.let { entries ->
                        pengajuanEntries = entries
                        Log.d("KonselingFragment", "Got Pengajuan Entries: $pengajuanEntries")
                        setupAdapter()
                    }
                }
            }

            override fun onFailure(call: Call<List<PengajuanResponse>>, t: Throwable) {
                Log.e("KonselingFragment", "Error: ${t.message}")
            }
        })
    }

    private fun getKonselorList() {
        val retrofit = ApiConfig.getApiService(requireContext())
        val call = retrofit.getKonselorname()
        call.enqueue(object : Callback<List<PengajuanResponse>> {
            override fun onResponse(call: Call<List<PengajuanResponse>>, response: Response<List<PengajuanResponse>>) {
                if (response.isSuccessful) {
                    response.body()?.let { konselors ->
                        konselorMap = konselors.associateBy { it.konselorId }
                        Log.d("KonselingFragment", "Got Konselor List: $konselorMap")
                        setupAdapter()
                    }
                } else {
                    Log.e("KonselingFragment", "Failed to get konselor data")
                }
            }

            override fun onFailure(call: Call<List<PengajuanResponse>>, t: Throwable) {
                Log.e("KonselingFragment", "Error: ${t.message}")
            }
        })
    }

    private fun getIdJournal(id: String) {
        val retrofit = ApiConfig.getApiService(requireContext())
        val retrofitData = retrofit.getPengguna(id)

        retrofitData.enqueue(object : Callback<List<UserResponse>?> {
            override fun onResponse(
                p0: Call<List<UserResponse>?>,
                p1: Response<List<UserResponse>?>
            ) {
                val responseBody = p1.body()
                if (!responseBody.isNullOrEmpty()) {
                    val userId = responseBody[0].id
                    if (userId != null) {
                        Preferences().saveId(userId, requireContext())
                        Log.d("getIdJournal", "User ID saved: $userId")
                    } else {
                        Log.e("getIdJournal", "User ID is null")
                    }
                } else {
                    Log.e("getIdJournal", "Response body is null or empty")
                }
            }

            override fun onFailure(p0: Call<List<UserResponse>?>, p1: Throwable) {
                Log.e("getIdJournal", "Error: ${p1.message}")
            }
        })
    }

    private fun getPengajuanData() {
        val apiService = ApiConfig.getApiService(requireContext())
        val call = apiService.getPengajuan()

        call.enqueue(object : Callback<List<PengajuanResponse>> {
            override fun onResponse(
                call: Call<List<PengajuanResponse>>,
                response: Response<List<PengajuanResponse>>
            ) {
                if (response.isSuccessful) {
                    val pengajuanList = response.body()
                    if (pengajuanList != null) {
                        Log.d("Pengajuan Info", "Data fetched successfully")
                    }
                } else {
                    Log.e("API Error", "Failed to fetch data")
                }
            }

            override fun onFailure(call: Call<List<PengajuanResponse>>, t: Throwable) {
                Log.e("API Error", "Error: ${t.message}")
            }
        })
    }

    private fun setupAdapter() {
        if (pengajuanEntries.isNotEmpty()) {
            Log.d("KonselingFragment", "Setting up adapter with data")
            binding.listPengajuan.adapter = ListPengajuanAdapter(pengajuanEntries)
        } else {
            Log.d("KonselingFragment", "Waiting for data to load: pengajuanEntries.size=${pengajuanEntries.size}, konselorMap.size=${konselorMap.size}")
        }
    }
}
