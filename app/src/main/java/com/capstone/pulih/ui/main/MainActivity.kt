package com.capstone.pulih.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.capstone.pulih.R
import com.capstone.pulih.databinding.ActivityMainBinding
import com.capstone.pulih.ui.main.adapter.ViewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewPager : ViewPager2
    private lateinit var bottomNav : BottomNavigationView
    private lateinit var adapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.vpUser
        bottomNav = binding.bnUser
        adapter = ViewPagerAdapter(supportFragmentManager,lifecycle)
        viewPager.adapter = adapter

//        Bottom Navigation Item Selected
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.user_home -> viewPager.currentItem = 0
                R.id.user_journaling -> viewPager.currentItem = 1
                R.id.user_konseling -> viewPager.currentItem = 2
                R.id.user_profile -> viewPager.currentItem = 3
            }
            true
        }

//        View Pager Callback
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val menuItem = bottomNav.menu.getItem(position)
                bottomNav.selectedItemId = menuItem.itemId
            }
        })

    }

}