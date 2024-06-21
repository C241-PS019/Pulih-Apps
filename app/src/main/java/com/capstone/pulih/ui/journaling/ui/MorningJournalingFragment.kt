package com.capstone.pulih.ui.journaling.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pulih.data.response.JournalResponseEvening
import com.capstone.pulih.data.response.JournalResponseMorning
import com.capstone.pulih.data.service.ApiConfig
import com.capstone.pulih.databinding.FragmentMorningJournalingBinding
import com.capstone.pulih.ui.journaling.adapter.JournalMorningAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MorningJournalingFragment : Fragment() {
    private var _binding: FragmentMorningJournalingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMorningJournalingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.morningJournalingItem.layoutManager = LinearLayoutManager(context)

        fetchMorningJournalEntries()
    }

    private fun fetchMorningJournalEntries() {
        val retrofit = ApiConfig.getApiService(requireContext())
        val call = retrofit.getJournalMorning()
        call.enqueue(object : Callback<List<JournalResponseMorning>> {
            override fun onResponse(call: Call<List<JournalResponseMorning>>, response: Response<List<JournalResponseMorning>>) {
                if (response.isSuccessful) {
                    response.body()?.let { journalEntries ->
                        binding.morningJournalingItem.adapter = JournalMorningAdapter(journalEntries)
                    }
                } else {
                    Log.e("MorningJournalFragment", "Failed to fetch data")
                }
            }

            override fun onFailure(call: Call<List<JournalResponseMorning>>, t: Throwable) {
                Log.e("MorningJournalFragment", "Error: ${t.message}")
            }
        })
    }
}
