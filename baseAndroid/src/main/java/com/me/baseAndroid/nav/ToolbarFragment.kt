package com.me.baseAndroid.nav

import android.os.Bundle
import android.view.View
import com.me.baseAndroid.base.BaseFragment
import kotlinx.android.synthetic.main.base_arch_module_layout_toolbar.*

/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 5/1/20 11:05 PM
 *  * Last modified 5/1/20 11:05 PM
 *
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