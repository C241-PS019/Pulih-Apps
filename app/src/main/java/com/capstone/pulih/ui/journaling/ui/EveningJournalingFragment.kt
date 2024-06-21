package com.capstone.pulih.ui.journaling.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pulih.data.response.JournalResponseEvening
import com.capstone.pulih.data.service.ApiConfig
import com.capstone.pulih.databinding.FragmentEveningJournalingBinding
import com.capstone.pulih.ui.journaling.adapter.JournalEveningAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EveningJournalingFragment : Fragment() {
    private var _binding: FragmentEveningJournalingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEveningJournalingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.EveningJournalingItem.layoutManager = LinearLayoutManager(context)

        fetchEveningJournalEntries()
    }

    private fun fetchEveningJournalEntries() {
        val retrofit = ApiConfig.getApiService(requireContext())
        val call = retrofit.getJournalEvening()
        call.enqueue(object : Callback<List<JournalResponseEvening>> {
            override fun onResponse(call: Call<List<JournalResponseEvening>>, response: Response<List<JournalResponseEvening>>) {
                if (response.isSuccessful) {
                    response.body()?.let { journalEntries ->
                        binding.EveningJournalingItem.adapter = JournalEveningAdapter(journalEntries)
                    }
                } else {
                    Log.e("MorningJournalFragment", "Failed to fetch data")
                }
            }

            override fun onFailure(call: Call<List<JournalResponseEvening>>, t: Throwable) {
                Log.e("MorningJournalFragment", "Error: ${t.message}")
            }
        })
    }
}