package com.me.baseAndroid.multinav

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.me.baseAndroid.R
import com.me.baseAndroid.base.AlertDisconnectionActivity
import com.me.baseAndroid.nav.INav
import com.me.baseAndroid.nav.NavFragment
import com.me.baseAndroid.view.executeIfAdded
import kotlinx.android.synthetic.main.base_arch_module_multi_nav_activity.*
import ru.dimakron.multistacks_lib.BackResultType
import ru.dimakron.multistacks_lib.MultiStacks

/**
 * Created by Abanoub Hanna.
 */
abstract class BaseMultiNavActivity : AlertDisconnectionActivity(), INav {
    override val layoutId = R.layout.base_arch_module_multi_nav_activity
    abstract val selectedTabIndex: Int
    abstract val bottomMenuId: Int
    abstract val navTabFragments: List<() -> Fragment>
    abstract val navTabFragmentsMap: HashMap<Int, Int>

    open fun onNonNavTabSelected(menItemId: Int) {}

    private lateinit var multiStacks: MultiStacks


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bottom_nav.inflateMenu(bottomMenuId)

        multiStacks = MultiStacks.Builder(supportFragmentManager, R.id.nav_host_container)
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
            bottom_nav.selectedItemId = it
        }

        bottom_nav.setOnNavigationItemSelectedListener {
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
        bottom_nav.setOnNavigationItemSelectedListener(null)
        super.onDestroy()
    }

    override fun onBackPressed() {
        val result = multiStacks.onBackPressed()
        if (result.type == BackResultType.CANCELLED) {
            super.onBackPressed()
        } else {
            result.newIndex?.let { newIndex ->
                navTabFragmentsMap.filterValues { it == newIndex }.keys.firstOrNull()?.let {
                    bottom_nav.selectedItemId = it
                }
            }
        }
    }


    override fun navigate(tabIndex: Int, clearAllTop: Boolean) {
        bottom_nav.selectedItemId = bottom_nav.menu.getItem(tabIndex).itemId
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