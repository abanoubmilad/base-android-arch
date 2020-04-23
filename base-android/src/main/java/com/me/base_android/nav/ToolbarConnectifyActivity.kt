package com.me.base_android.nav

import android.os.Bundle
import com.me.base_android.base.ConnectifyActivity
import kotlinx.android.synthetic.main.base_arch_module_layout_toolbar.*

/**
 * Created by Abanoub Hanna.
 */
abstract class ToolbarConnectifyActivity : ConnectifyActivity() {

    open fun onToolbarLeftButtonClick() {
        onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        iv_left.setOnClickListener {
            onToolbarLeftButtonClick()
        }
    }
}