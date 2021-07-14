package com.codepath.quarterstep.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.codepath.quarterstep.fragments.ArrangementFragment

class ScreenSlidePagerAdapter(
        private val fragmentList: ArrayList<ScreenSlidePageFragment>,
        fm: FragmentManager
) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int = fragmentList.size

    override fun getItem(position: Int): Fragment {
        if (position >= 0 && position < fragmentList.size)
            return fragmentList[position]
        return ScreenSlidePageFragment();
    }
}