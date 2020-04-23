package com.me.demo

import android.os.Bundle
import com.me.base_android.nav.HomeNavActivity
import com.me.demo.formscreen.Register
import com.me.demo.homescreen.Title
import com.me.demo.listscreen.Leaderboard

/**
 * An activity that inflates a layout that has a [BottomNavigationView].
 */
class MainActivity : HomeNavActivity() {

    override val layoutId = R.layout.activity_main
    override val bottomNavLayoutId = R.id.bottom_nav
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


    override fun onDisconnected() {
    }

    override fun onConnected() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigate(1)
    }
}
