package com.capstone.pulih.ui.mainkonselor

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.capstone.pulih.R
import com.capstone.pulih.databinding.ActivityMainBinding
import com.capstone.pulih.databinding.ActivityMainKonselorBinding
import com.capstone.pulih.ui.main.adapter.ViewPagerAdapter
import com.capstone.pulih.ui.mainkonselor.adapter.ViewPagerKonselorAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainKonselorActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainKonselorBinding
    private lateinit var viewPager : ViewPager2
    private lateinit var bottomNav : BottomNavigationView
    private lateinit var adapter: ViewPagerKonselorAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainKonselorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.vpUser
        bottomNav = binding.bnUser
        adapter = ViewPagerKonselorAdapter(supportFragmentManager,lifecycle)
        viewPager.adapter = adapter

        bottomNavigate()
        viewPager()

    }
    private fun viewPager(){
        //        View Pager Callback
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val menuItem = bottomNav.menu.getItem(position)
                bottomNav.selectedItemId = menuItem.itemId
            }
        })
    }

    private fun bottomNavigate(){
        //        Bottom Navigation Item Selected
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.user_home -> viewPager.currentItem = 0
                R.id.user_journaling -> viewPager.currentItem = 1
                R.id.user_profile -> viewPager.currentItem = 2
            }
            true
        }
    }
}