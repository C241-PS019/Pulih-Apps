package com.capstone.pulih.ui.onboarding.walkthrough

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.capstone.pulih.MainActivity
import com.capstone.pulih.R
import com.capstone.pulih.databinding.ActivityOnboardingBinding
import com.capstone.pulih.ui.auth.response.Pages
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pulihproject.pulih.onboarding.OnBoardingAdapter
import com.pulihproject.pulih.utils.gone
import com.pulihproject.pulih.utils.visible


class OnboardingActivity : AppCompatActivity(){

    private lateinit var pagerList : ArrayList<Pages>
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var onBoardingViewPager2 : ViewPager2
    private lateinit var skipBtn : Button
    private lateinit var nextBtn : Button
    private lateinit var prevBtn : Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBoardingViewPager2 = findViewById(R.id.viewPager)
        skipBtn = findViewById(R.id.skipBtn)
        nextBtn = findViewById(R.id.nextBtn)
        prevBtn = findViewById(R.id.previousBtn)

        pagerList = arrayListOf(
            Pages(getString(R.string.title1),
                R.drawable.img_1,getString(R.string.desc1)),
            Pages(getString(R.string.title2),
                R.drawable.img,getString(R.string.desc1)),
            Pages(getString(R.string.title3),
                R.drawable.img_2,getString(R.string.desc3)),
        )

        onBoardingViewPager2.apply {
            adapter = OnBoardingAdapter(this@OnboardingActivity,pagerList)
            registerOnPageChangeCallback(onBoardingPageChangeCallback)
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        TabLayoutMediator(tabLayout,onBoardingViewPager2){ _, _ -> }.attach()

        nextBtn.setOnClickListener{
            if (onBoardingViewPager2.currentItem < onBoardingViewPager2.adapter!!.itemCount -1){
                onBoardingViewPager2.currentItem += 1
            } else{
                homeScreenIntent()
            }

        }

        skipBtn.setOnClickListener{
            homeScreenIntent()
        }

        prevBtn.setOnClickListener{
            if (onBoardingViewPager2.currentItem > 0){
                onBoardingViewPager2.currentItem -=1
            }
        }
    }

    override fun onDestroy() {
        onBoardingViewPager2.unregisterOnPageChangeCallback(onBoardingPageChangeCallback)
        super.onDestroy()
    }

    private fun homeScreenIntent() {
        val homeIntent = Intent(this, MainActivity::class.java)
        startActivity(homeIntent)
    }

    private val onBoardingPageChangeCallback = object : ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            when(position){
                0 -> {
                    skipBtn.visible()
                    nextBtn.visible()
                    prevBtn.gone()
                }
                1-> {
                    skipBtn.visible()
                    nextBtn.visible()
                    prevBtn.visible()
                }
                2-> {
                    skipBtn.visible()
                    nextBtn.visible()
                    nextBtn.text = "Next"
                    prevBtn.visible()
                }
            }

        }

    }

}