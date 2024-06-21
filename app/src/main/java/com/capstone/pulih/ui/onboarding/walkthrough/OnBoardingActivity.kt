package com.capstone.pulih

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.capstone.pulih.databinding.ActivityMainBinding
import com.capstone.pulih.databinding.ActivityOnboardingBinding
import com.capstone.pulih.ui.onboarding.welcome.WelcomeActivity
import com.capstone.pulih.utils.Preferences

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        checkOnBoarding()

        val navHostFragment = supportFragmentManager.findFragmentById(binding.container.navHostFragmentContainer.id) as NavHostFragment? ?: return
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration.Builder(R.id.fragmentOnboarding).build()
        setupActionBarWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.fragmentOnboarding-> {
                    binding.toolbar.visibility = View.GONE
                }
            }
        }

    }

    private fun checkOnBoarding(){
        if(Preferences().getOnBoarding(applicationContext)){
            startActivity(Intent(this@OnBoardingActivity, WelcomeActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}