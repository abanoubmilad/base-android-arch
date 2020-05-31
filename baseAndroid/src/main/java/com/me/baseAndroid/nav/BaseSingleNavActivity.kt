package com.me.baseAndroid.nav

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.me.baseAndroid.R
import com.me.baseAndroid.base.AlertDisconnectionActivity
import com.me.baseAndroid.view.executeIfAdded

open class BaseSingleNavActivity : AlertDisconnectionActivity(), INav {

    override val layoutId = R.layout.base_arch_module_single_nav_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.addOnBackStackChangedListener {
            hideKeyboard()
            getCurrentFragment()?.executeIfAdded {
                (it as? NavFragment)?.onVisibleInternal()
            }
        }
    }

    override fun onBackPressed() {
        dismiss()
    }

    override fun dismiss(clearAllTop: Boolean) {
        var count = supportFragmentManager.backStackEntryCount
        if (clearAllTop) {
            while (count > 0) {
                count--
                supportFragmentManager.popBackStack()
            }
        } else if (count > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    override fun dismissThenNavigate(fragment: Fragment, bundle: Bundle?) {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        }
        navigate(fragment, bundle)
    }

    override fun getCurrentFragment(): Fragment? {
        return supportFragmentManager.fragments.lastOrNull()
    }


    override fun navigate(fragment: Fragment, bundle: Bundle?) {
        fragment.arguments = bundle
        supportFragmentManager
            .beginTransaction().addToBackStack(null)
            .replace(R.id.nav_host_container, fragment).commitAllowingStateLoss()
    }

    override fun navigate(tabIndex: Int, fragment: Fragment, bundle: Bundle?) {
    }

    override fun navigate(tabIndex: Int, clearAllTop: Boolean) {
    }
}
