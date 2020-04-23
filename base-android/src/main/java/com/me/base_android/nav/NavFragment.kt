package com.me.base_android.nav

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.me.base_android.base.BaseFragment

/**
 * Created by Abanoub Hanna.
 */
abstract class NavFragment : BaseFragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.isClickable = true
        view.isFocusable = true
    }

    open fun onVisible() {

    }


    fun navigate(tabIndex: Int) {
        (activity as? INav)?.navigate(tabIndex)

    }

    fun dismiss() {
        (activity as? INav)?.dismiss()
    }

    fun dismissThenNavigate(fragment: Fragment, bundle: Bundle? = null) {
        (activity as? INav)?.dismissThenNavigate(fragment, bundle)
    }

    fun navigate(fragment: Fragment, bundle: Bundle? = null) {
        (activity as? INav)?.navigate(fragment, bundle)
    }

    fun navigate(tabIndex: Int, fragment: Fragment, bundle: Bundle? = null) {
        (activity as? INav)?.navigate(tabIndex, fragment, bundle)
    }
}