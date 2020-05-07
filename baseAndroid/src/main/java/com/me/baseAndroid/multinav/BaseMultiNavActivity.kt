/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 5/7/20 3:59 AM
 *  * Last modified 5/7/20 3:59 AM
 *
 */

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

/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 5/1/20 11:05 PM
 *  * Last modified 5/1/20 11:05 PM
 *
 */
abstract class BaseMultiNavActivity : AlertDisconnectionActivity(), INav {
    override val layoutId = R.layout.base_arch_module_multi_nav_activity
    abstract val selectedTabIndex: Int
    abstract val bottomMenuId: Int
    abstract val navTabFragments: List<() -> Fragment>
    abstract val navTabFragmentsMap: HashMap<Int, Int>
    open val enableClearTabOnClick: Boolean = true

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

        getMenuItemIdOfIndex(selectedTabIndex)?.let {
            bottom_nav.selectedItemId = it
        }

        bottom_nav.setOnNavigationItemSelectedListener {
            hideKeyboard()

            val targetIndex = navTabFragmentsMap[it.itemId]
            if (targetIndex != null) {
                onNavTabClick(targetIndex)
                true
            } else {
                onNonNavTabSelected(it.itemId)
                false
            }
        }
    }

    private fun onNavTabClick(index: Int) {
        if (index == multiStacks.getSelectedTabIndex()) {
            if (enableClearTabOnClick) {
                multiStacks.clearStack()
            } else {
                multiStacks.popFragments(1)
            }
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
                getMenuItemIdOfIndex(newIndex)?.let {
                    bottom_nav.selectedItemId = it
                }
            }
        }
    }

    private fun getMenuItemIdOfIndex(index: Int): Int? {
        return navTabFragmentsMap.filterValues { it == index }.keys.firstOrNull()
    }

    override fun navigate(tabIndex: Int, clearAllTop: Boolean) {
        getMenuItemIdOfIndex(tabIndex)?.let {
            bottom_nav.selectedItemId = it
            if (clearAllTop)
                multiStacks.clearStack()
        }
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


    override fun navigate(tabIndex: Int, fragment: Fragment, bundle: Bundle?) {
        navigate(tabIndex)
        navigate(fragment, bundle)
    }

    open fun onNavTabSelected(index: Int) {

    }

    override fun getCurrentFragment() = multiStacks.getCurrentFragment()


}