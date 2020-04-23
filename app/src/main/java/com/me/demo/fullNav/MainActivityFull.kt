package com.me.demo.fullNav

import android.os.Bundle
import com.me.base_android.fullNav.HomeNavActivityFull
import com.me.demo.R
import com.me.demo.formscreen.Register
import com.me.demo.homescreen.Title
import com.me.demo.listscreen.Leaderboard

/**
 * An activity that inflates a layout that has a [BottomNavigationView].
 */
class MainActivityFull : HomeNavActivityFull() {
    override val navContainerId = R.id.nav_host_container
    override val navTabFragments = listOf(
        { Title() },
        { Leaderboard() },
        { Register() }
    )
    override val navTabFragmentsMap = { id: Int ->
        when (id) {
            R.id.home -> 0
            R.id.list -> 1
            else -> 2
        }
    }

    override val layoutId = R.layout.activity_main_full
    override val bottomNavLayoutId = R.id.bottom_nav
    override fun onDisconnected() {
    }

    override fun onConnected() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigate(1)
    }

}
