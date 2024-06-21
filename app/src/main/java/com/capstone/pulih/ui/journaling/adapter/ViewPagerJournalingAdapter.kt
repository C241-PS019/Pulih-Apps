package com.capstone.pulih.ui.journaling.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.capstone.pulih.R
import com.capstone.pulih.ui.journaling.ui.EveningJournalingFragment
import com.capstone.pulih.ui.journaling.ui.MorningJournalingFragment

class ViewPagerJournalingAdapter(private val mCtx: Context, fm: FragmentManager, data: Bundle) :
    FragmentPagerAdapter(
        fm,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
    private var fragmentBundle: Bundle = data

    @StringRes
    private val tabs = intArrayOf(R.string.titletab1, R.string.titletab2)

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = MorningJournalingFragment()
            1 -> fragment = EveningJournalingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mCtx.resources.getString(tabs[position])
    }

}

