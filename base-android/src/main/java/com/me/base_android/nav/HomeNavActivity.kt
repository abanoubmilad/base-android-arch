package com.me.base_android.nav

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.me.base_android.base.ConnectifyActivity
import com.me.base_android.view.executeIfAdded

/**
 * Created by Abanoub Hanna.
 */
abstract class HomeNavActivity : ConnectifyActivity(), INav {
    abstract val bottomNavLayoutId: Int
    abstract val navContainerId: Int

    abstract val navTabFragments: List<() -> Fragment>
    abstract val navTabFragmentsMap: (Int) -> Int?

    open fun onNonNavTabSelected(menItemId: Int) {}

    private var currentIndex = -1

    private val bottomNavView: BottomNavigationView by lazy {
        findViewById<BottomNavigationView>(bottomNavLayoutId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bottomNavView.setOnNavigationItemSelectedListener {
            val targetIndex = navTabFragmentsMap(it.itemId)
            if (targetIndex != null)
                changeTab(targetIndex)
            else
                onNonNavTabSelected(it.itemId)
            true
        }

        supportFragmentManager.addOnBackStackChangedListener {
            supportFragmentManager.fragments.lastOrNull()?.executeIfAdded {
                (it as? NavFragment)?.onVisible()
            }
        }
    }

    override fun dismiss(clearAllTop: Boolean) {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            dismiss()
        } else {
            finish()
        }

    }

    override fun dismissThenNavigate(fragment: Fragment, bundle: Bundle?) {
        dismiss()
        navigate(fragment, bundle)
    }

    override fun navigate(fragment: Fragment, bundle: Bundle?) {
        fragment.arguments = bundle
        supportFragmentManager
            .beginTransaction().addToBackStack(null)
            .add(navContainerId, fragment).commitAllowingStateLoss()
    }

    override fun navigate(tabIndex: Int, fragment: Fragment, bundle: Bundle?) {
        navigate(tabIndex)
        navigate(fragment, bundle)
    }

    override fun navigate(tabIndex: Int, clearAllTop: Boolean) {
        bottomNavView.selectedItemId = (bottomNavView.menu.getItem(tabIndex) as MenuItem).itemId
    }

    private fun changeTab(tabIndex: Int) {
        val toClear = supportFragmentManager.backStackEntryCount
        if (tabIndex != currentIndex) {
            currentIndex = tabIndex
            for (i in 0 until toClear)
                supportFragmentManager.popBackStack()
            navigate(navTabFragments[tabIndex].invoke())
        } else {
            if (toClear > 1) {
                supportFragmentManager.popBackStack()
            }

        }
    }

}

