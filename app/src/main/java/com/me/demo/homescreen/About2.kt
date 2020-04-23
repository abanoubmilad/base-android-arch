package com.me.demo.homescreen

import android.os.Bundle
import android.view.View
import com.me.base_android.nav.NavFragment
import com.me.demo.R
import kotlinx.android.synthetic.main.fragment_about2.*

/**
 * Shows "About"
 */
class About2 : NavFragment() {
    override val layoutId = R.layout.fragment_about2


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn.setOnClickListener {
            navigate(About3())

        }
    }

    override fun onVisible() {
        super.onVisible()

        showCookieBar("about 2")
    }
}
