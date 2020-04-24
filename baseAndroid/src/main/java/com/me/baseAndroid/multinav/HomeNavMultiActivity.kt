package com.me.baseAndroid.multinav

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.me.baseAndroid.base.ConnectifyActivity
import com.me.baseAndroid.nav.INav
import com.me.baseAndroid.nav.NavFragment
import com.me.baseAndroid.view.executeIfAdded
import ru.dimakron.multistacks_lib.BackResultType
import ru.dimakron.multistacks_lib.MultiStacks

/**
 * Created by Abanoub Hanna.
 */
abstract class HomeNavMultiActivity : ConnectifyActivity(), INav {
    abstract val bottomNavLayoutId: Int
    abstract val selectedTabIndex: Int
    abstract val navContainerId: Int
    abstract val navTabFragments: List<() -> Fragment>
    abstract val navTabFragmentsMap: HashMap<Int, Int>

    open fun onNonNavTabSelected(menItemId: Int) {}

    private lateinit var multiStacks: MultiStacks

    private val bottomNavView: BottomNavigationView by lazy {
        findViewById<BottomNavigationView>(bottomNavLayoutId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        multiStacks = MultiStacks.Builder(supportFragmentManager, navContainerId)
            .setState(savedInstanceState)
            .setRootFragmentInitializers(navTabFragments)
            .setSelectedTabIndex(selectedTabIndex)
            .setTabHistoryEnabled(true)
            .setTransactionListener(object : MultiStacks.TransactionListener {

                override fun onFragmentTransaction(fragment: Fragment?) {
                    fragment.executeIfAdded {
                        (it as? NavFragment)?.onVisibleInternal()
                    }
                }

                override fun onTabTransaction(fragment: Fragment?, index: Int) {
                    fragment.executeIfAdded {
                        (it as? NavFragment)?.onVisibleInternal()
                    }
                }
            })
            .build()

        navTabFragmentsMap.filterValues { it == selectedTabIndex }.keys.firstOrNull()?.let {
            bottomNavView.selectedItemId = it
        }

        bottomNavView.setOnNavigationItemSelectedListener {
            hideKeyboard()

            val targetIndex = navTabFragmentsMap[it.itemId]
            if (targetIndex != null)
                onNavTabClick(targetIndex)
            else
                onNonNavTabSelected(it.itemId)
            true
        }
    }

    private fun onNavTabClick(index: Int) {
        if (index == multiStacks.getSelectedTabIndex()) {
            multiStacks.popFragments(1)
        } else {
            multiStacks.setSelectedTabIndex(index)
            onNavTabSelected(index)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        multiStacks.saveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        bottomNavView.setOnNavigationItemSelectedListener(null)
        super.onDestroy()
    }

    override fun onBackPressed() {
        val result = multiStacks.onBackPressed()
        if (result.type == BackResultType.CANCELLED) {
            super.onBackPressed()
        } else {
            result.newIndex?.let { newIndex ->
                navTabFragmentsMap.filterValues { it == newIndex }.keys.firstOrNull()?.let {
                    bottomNavView.selectedItemId = it
                }
            }
        }
    }


    override fun navigate(tabIndex: Int, clearAllTop: Boolean) {
        bottomNavView.selectedItemId = bottomNavView.menu.getItem(tabIndex).itemId
        if (clearAllTop)
            multiStacks.clearStack()
    }

    override fun dismiss(clearAllTop: Boolean) {
        if (clearAllTop)
            multiStacks.clearStack()
        else
            multiStacks.popFragments(1)
    }

    override fun dismissThenNavigate(fragment: Fragment, bundle: Bundle?) {
        multiStacks.popFragments(1)
        navigate(fragment, bundle)
    }

    override fun navigate(fragment: Fragment, bundle: Bundle?) {
        fragment.arguments = bundle
        multiStacks.push(fragment)
    }

    fun navigateSingleTop(
        clearTop: Boolean = false,
        navFragmentClass: Class<*>,
        navFragmentCall: () -> Fragment,
        bundle: Bundle? = null
    ) {
//        multiStacks.
//        val found = multiStacks.popUntil(navFragmentClass, clearTop)
//        if (!found || clearTop) {
//            val fragment = navFragmentCall()
//            fragment.arguments = bundle
//            multiStacks.push(fragment)
//        }

    }

    fun navigateSingleTop(
        tabIndex: Int,
        clearTop: Boolean = false,
        navFragmentClass: Class<*>,
        navFragmentCall: () -> Fragment,
        bundle: Bundle? = null
    ) {
        navigate(tabIndex)
        navigateSingleTop(
            clearTop,
            navFragmentClass,
            navFragmentCall,
            bundle
        )

    }

    override fun navigate(tabIndex: Int, fragment: Fragment, bundle: Bundle?) {
        navigate(tabIndex)
        navigate(fragment, bundle)
    }

    open fun onNavTabSelected(index: Int) {

    }

    fun getCurrentFragment() = multiStacks.getCurrentFragment()


}