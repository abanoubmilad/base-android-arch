package com.me.demo.formscreen

import com.me.baseAndroid.nav.NavFragment
import com.me.demo.R

/**
 * Shows "Done".
 */
class Registered : NavFragment() {
    override val layoutId = R.layout.fragment_registered


    override fun onCreated() {


    }

    override fun onVisible() {

        showCookieBar("Registered")
    }
}
