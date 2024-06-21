package com.capstone.pulih.ui.journaling.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.capstone.pulih.R
import com.capstone.pulih.databinding.FragmentJournalingBinding
import com.capstone.pulih.ui.journaling.adapter.ViewPagerJournalingAdapter

class JournalingFragment(
    private val fm: FragmentManager
) : Fragment() {


    companion object {
    const val JOURNALING = "extra_username"
    @StringRes
    private val TAB_TITLES = intArrayOf(
        R.string.titletab1,
        R.string.titletab2
    )
}

    private var _binding: FragmentJournalingBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = Bundle().apply {
           JOURNALING
        }

        setupViewPager(bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJournalingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    private fun setupViewPager(bundle: Bundle) {
        val sectionPagerAdapter = ViewPagerJournalingAdapter(requireContext(), fm, bundle)
        binding.viewPager.adapter = sectionPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)

        binding.btnJournaling.setOnClickListener{
            val intent = Intent(activity, CreateJournalingMorningActivity::class.java)
            startActivity(intent)

        }

        for (i in TAB_TITLES.indices) {
            binding.tabs.getTabAt(i)?.setText(TAB_TITLES[i])
        }
    }


}