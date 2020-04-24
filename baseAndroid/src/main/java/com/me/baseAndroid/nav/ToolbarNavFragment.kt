package com.me.baseAndroid.nav

import androidx.annotation.CallSuper
import kotlinx.android.synthetic.main.base_arch_module_layout_toolbar.*

/**
 * Created by Abanoub Hanna.
 */
abstract class ToolbarNavFragment : NavFragment() {

    open fun onToolbarLeftButtonClick() {
        activity?.onBackPressed()
    }

    @CallSuper
    override fun onCreated() {

        iv_left.setOnClickListener {
            onToolbarLeftButtonClick()
        }
    }
}