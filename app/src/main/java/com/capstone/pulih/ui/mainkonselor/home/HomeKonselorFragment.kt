package com.capstone.pulih.ui.mainkonselor.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.capstone.pulih.databinding.FragmentHomeKonselorBinding
import com.capstone.pulih.ui.detection.DetectionActivity
import com.capstone.pulih.ui.journaling.ui.CreateJournalingMorningActivity
import com.capstone.pulih.ui.konselingpage.KonselingInfoActivity
import com.capstone.pulih.ui.konselormitra.KonselorMitraActivity
import com.capstone.pulih.ui.mainkonselor.konseling.WorkspaceActivity
import com.capstone.pulih.ui.mainkonselor.konselorguide.KonselorGuideActivity
import com.capstone.pulih.ui.viewmodel.HomeKonselorViewModel
import com.capstone.pulih.ui.viewmodel.HomeViewModel
import com.capstone.pulih.utils.Preferences


class HomeKonselorFragment : Fragment() {
    private val viewModel : HomeKonselorViewModel by activityViewModels()
    private var _binding: FragmentHomeKonselorBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeKonselorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUser()
        binding.cardPulih.setOnClickListener {
            val intent = Intent(requireContext(), KonselorGuideActivity::class.java)
            startActivity(intent)
        }
        binding.btnKonseling.setOnClickListener {
            val intent = Intent(requireContext(), WorkspaceActivity::class.java)
            startActivity(intent)
        }
        binding.btnJournal.setOnClickListener {
            val intent = Intent(requireContext(), CreateJournalingMorningActivity::class.java)
            startActivity(intent)
        }
        binding.cardKonselor.setOnClickListener {
            val intent = Intent(requireContext(), KonselorMitraActivity::class.java)
            startActivity(intent)
        }
        binding.cardKonseling.setOnClickListener {
            val intent = Intent(requireContext(), KonselingInfoActivity::class.java)
            startActivity(intent)
        }
        binding.btnDeteksi.setOnClickListener {
            val intent = Intent(requireContext(), DetectionActivity::class.java)
            startActivity(intent)
        }
    }
    private fun setUser(){
        viewModel.getNickName(Preferences().getNick(requireContext()).toString())
        viewModel.name.observe(viewLifecycleOwner){
            binding.tvUser.text = it.toString()
        }
    }
}