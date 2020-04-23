package com.me.demo.formscreen

import com.me.base_android.nav.NavFragment
import com.me.demo.R

/**
 * Shows "Done".
 */
class Registered : NavFragment() {
    override val layoutId = R.layout.fragment_registered


    override fun onVisible() {
        super.onVisible()

        showCookieBar("Registered")
    }
}
