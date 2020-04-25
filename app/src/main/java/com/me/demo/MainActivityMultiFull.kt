package com.me.demo

import com.me.baseAndroid.multinav.BaseMultiNavActivity
import com.me.demo.formscreen.Register
import com.me.demo.homescreen.Title
import com.me.demo.listscreen.Leaderboard

/**
 * An activity that inflates a layout that has a [BottomNavigationView].
 */
class MainActivityMultiFull : BaseMultiNavActivity() {
    override val bottomMenuId = R.menu.bottom_nav
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
    override val selectedTabIndex = 1

}
