package com.me.baseAndroid.nav

import android.os.Bundle
import com.me.baseAndroid.base.BaseActivity
import kotlinx.android.synthetic.main.base_arch_module_layout_toolbar.*

/**
 * Created by Abanoub Hanna.
 */
abstract class ToolbarActivity : BaseActivity() {

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