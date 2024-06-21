package com.capstone.pulih.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.capstone.pulih.databinding.FragmentHomeBinding
import com.capstone.pulih.ui.artikel.NewsActivity
import com.capstone.pulih.ui.detection.DetectionActivity
import com.capstone.pulih.ui.journaling.ui.CreateJournalingMorningActivity
import com.capstone.pulih.ui.konselingpage.KonselingInfoActivity
import com.capstone.pulih.ui.konselormitra.KonselorMitraActivity
import com.capstone.pulih.ui.main.konseling.PengajuanActivity
import com.capstone.pulih.ui.pulihpage.PulihActivity
import com.capstone.pulih.ui.viewmodel.HomeViewModel
import com.capstone.pulih.utils.Preferences

class HomeFragment : Fragment() {
    private val viewModel : HomeViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUser()
        binding.btnJournal.setOnClickListener{
            val intent = Intent(activity, CreateJournalingMorningActivity::class.java)
            startActivity(intent)
        }
        binding.btnDeteksi.setOnClickListener {
            val intent = Intent(activity, DetectionActivity::class.java)
            startActivity(intent)
        }
        binding.btnArtikel.setOnClickListener {
            val intent = Intent(activity, NewsActivity::class.java)
            startActivity(intent)
        }

        binding.btnKonseling.setOnClickListener {
            val intent = Intent(activity, PengajuanActivity::class.java)
            startActivity(intent)
        }

        binding.cardPulih.setOnClickListener {
            val intent = Intent(requireContext(), PulihActivity::class.java)
            startActivity(intent)
        }
        binding.cardKonseling.setOnClickListener {
            val intent = Intent(requireContext(), KonselingInfoActivity::class.java)
            startActivity(intent)
        }
        binding.cardKonselor.setOnClickListener {
            val intent = Intent(requireContext(), KonselorMitraActivity::class.java)
            startActivity(intent)
        }
    }


    private fun setUser(){
        viewModel.getNick(Preferences().getNick(requireContext()).toString())
        viewModel.name.observe(viewLifecycleOwner){
            binding.tvUser.text = it.toString()
        }
    }
}