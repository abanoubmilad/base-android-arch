package com.me.demo.homescreen

import com.me.demo.R

/**
 * Shows "About"
 */
class About3 : NavFragment() {
    override val layoutId = R.layout.fragment_about3


    override fun onCreated() {

    }


    override fun onVisible() {

        showCookieBar("about 3")
    }
}
