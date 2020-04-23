package com.me.base_android.fullNav

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class NavPager(private val fragmentsCount: Int, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val fragments: List<Fragment> by lazy {
        val list = mutableListOf<Fragment>()
        for (i in 0 until fragmentsCount)
            list.add(DummyFragment(i))
        list
    }

    override fun getItem(i: Int) = fragments[i]
    override fun getCount() = fragmentsCount
}