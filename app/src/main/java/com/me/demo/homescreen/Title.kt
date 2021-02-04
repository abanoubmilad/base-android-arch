package com.me.demo.homescreen

import com.me.demo.R

/**
 * Shows the main title screen with a button that navigates to [About].
 */
class Title : NavFragment() {
    override val layoutId = R.layout.fragment_title

    override fun onCreated() {
        about_btn.setOnClickListener {
            INav?.navigate(About())
        }

    }


    override fun onVisible() {

        showCookieBar("title")
    }
}
