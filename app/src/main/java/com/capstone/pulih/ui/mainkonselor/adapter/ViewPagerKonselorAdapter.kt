package com.capstone.pulih.ui.mainkonselor.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capstone.pulih.ui.journaling.ui.JournalingFragment
import com.capstone.pulih.ui.main.home.HomeFragment
import com.capstone.pulih.ui.main.konseling.KonselingFragment
import com.capstone.pulih.ui.main.profile.ProfileFragment
import com.capstone.pulih.ui.mainkonselor.home.HomeKonselorFragment

class ViewPagerKonselorAdapter(private val fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {

        return when(position){
            0 -> {
                HomeKonselorFragment()
            }
            1 -> {
                JournalingFragment(fragmentManager)
            }
            2 -> {
                ProfileFragment()
            }
            else -> {
                HomeKonselorFragment()
            }
        }
    }


}