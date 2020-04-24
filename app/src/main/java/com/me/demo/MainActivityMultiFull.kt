package com.me.demo

import com.me.baseAndroid.multinav.HomeNavMultiActivity
import com.me.demo.formscreen.Register
import com.me.demo.homescreen.Title
import com.me.demo.listscreen.Leaderboard

/**
 * An activity that inflates a layout that has a [BottomNavigationView].
 */
class MainActivityMultiFull : HomeNavMultiActivity() {
    override val navContainerId = R.id.nav_host_container
    override val navTabFragments = listOf(
        { Title() },
        { Leaderboard() },
        { Register() }
    )
    override val navTabFragmentsMap = hashMapOf(
        R.id.home to 0,
        R.id.list to 1,
        R.id.form to 2

    )

    override val layoutId = R.layout.activity_main
    override val bottomNavLayoutId = R.id.bottom_nav
    override val selectedTabIndex = 1

    override fun onDisconnected() {
    }

    override fun onConnected() {
    }

}
