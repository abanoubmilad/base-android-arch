package com.me.base_android.nav

import android.os.Bundle
import android.view.View
import com.me.base_android.base.BaseFragment
import kotlinx.android.synthetic.main.base_arch_module_layout_toolbar.*

/**
 * Created by Abanoub Hanna.
 */
abstract class ToolbarFragment : BaseFragment() {

    open fun onToolbarLeftButtonClick() {
        activity?.onBackPressed()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iv_left.setOnClickListener {
            onToolbarLeftButtonClick()
        }
    }
}