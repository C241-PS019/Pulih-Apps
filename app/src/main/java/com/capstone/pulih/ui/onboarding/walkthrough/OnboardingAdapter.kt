package com.pulihproject.pulih.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capstone.pulih.ui.auth.response.Pages
import com.capstone.pulih.ui.onboarding.walkthrough.OnboardingFragment

class OnBoardingAdapter(activity: FragmentActivity, private val pagerList : ArrayList<Pages>): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return pagerList.size
    }

    override fun createFragment(position: Int): Fragment {
        return OnboardingFragment(pagerList[position])
    }


}