package com.me.base_android.fullNav

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.me.base_android.R
import com.me.base_android.base.ConnectifyActivity
import com.me.base_android.nav.INav
import com.me.base_android.nav.NavFragment
import com.me.base_android.view.executeIfAdded

/**
 * Created by Abanoub Hanna.
 */
abstract class HomeNavActivityFull : ConnectifyActivity(), INav {
    abstract val bottomNavLayoutId: Int
    abstract val navContainerId: Int
    abstract val navTabFragments: List<() -> Fragment>
    abstract val navTabFragmentsMap: (Int) -> Int?

    open fun onNonNavTabSelected(menItemId: Int) {}

    private val bottomNavView: BottomNavigationView by lazy {
        findViewById<BottomNavigationView>(bottomNavLayoutId)
    }
    private val viewPager: ViewPager by lazy {
        findViewById<ViewPager>(navContainerId)
    }

    private val activeRootFragment: Fragment?
        get() = navPager.getItem(viewPager.currentItem)


    private val navPager: NavPager by lazy {
        NavPager(navTabFragments.size, supportFragmentManager)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewPager.adapter = navPager
        viewPager.offscreenPageLimit = navTabFragments.size

        bottomNavView.setOnNavigationItemSelectedListener {
            val targetIndex = navTabFragmentsMap(it.itemId)
            if (targetIndex != null)
                onTabClick(targetIndex)
            else
                onNonNavTabSelected(it.itemId)
            true
        }
    }

    private fun onTabClick(index: Int) {
        hideKeyboard()

        if (viewPager.currentItem != index) {
            viewPager.currentItem = index
            onBackStackChanged()
        } else {
            activeRootFragment?.executeIfAdded {
                if (it.childFragmentManager.backStackEntryCount > 1) {
                    it.childFragmentManager.popBackStackImmediate()
                }

            }
        }
    }


    fun onBackStackChanged() {
        activeRootFragment?.executeIfAdded {
            it.childFragmentManager.fragments.lastOrNull()?.executeIfAdded { last ->
                (last as? NavFragment)?.onVisible()
            }
        }

    }

    override fun onBackPressed() {
        activeRootFragment?.executeIfAdded {
            if (it.childFragmentManager.backStackEntryCount > 1) {
                it.childFragmentManager.popBackStackImmediate()
            } else
                finish()
        }
    }

    private fun displayFragment(
        fragment: Fragment,
        bundle: Bundle? = null
    ) {
        activeRootFragment.executeIfAdded {
            fragment.arguments = bundle
            it.childFragmentManager
                .beginTransaction().addToBackStack(null)
                .add(R.id.fl_container, fragment)
                .commitAllowingStateLoss()
        }
    }


    private fun popFragment() {
        activeRootFragment.executeIfAdded {
            it.childFragmentManager.popBackStackImmediate()
        }
    }

    private fun clearStack() {
        activeRootFragment.executeIfAdded {
            val toDelete = it.childFragmentManager.backStackEntryCount - 1
            if (toDelete > 0) {
                for (i in 0 until toDelete)
                    it.childFragmentManager.popBackStackImmediate()
            }
        }
    }


    override fun navigate(tabIndex: Int, clearAllTop: Boolean) {
        bottomNavView.selectedItemId = bottomNavView.menu.getItem(tabIndex).itemId
        if (clearAllTop)
            clearStack()
    }

    override fun dismiss(clearAllTop: Boolean) {
        if (clearAllTop)
            clearStack()
        else
            popFragment()
    }

    override fun dismissThenNavigate(fragment: Fragment, bundle: Bundle?) {
        popFragment()
        navigate(fragment, bundle)
    }

    override fun navigate(fragment: Fragment, bundle: Bundle?) {
        displayFragment(fragment, bundle)
    }

    override fun navigate(tabIndex: Int, fragment: Fragment, bundle: Bundle?) {
        navigate(tabIndex)
        navigate(fragment, bundle)
    }

}

