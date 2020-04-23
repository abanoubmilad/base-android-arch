package com.me.demo.homescreen

import android.os.Bundle
import android.view.View
import com.me.base_android.nav.NavFragment
import com.me.demo.R

/**
 * Shows "About"
 */
class About3 : NavFragment() {
    override val layoutId = R.layout.fragment_about3


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onVisible() {
        super.onVisible()

        showCookieBar("about 3")
    }
}
