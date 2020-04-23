package com.me.demo.homescreen

import android.os.Bundle
import android.view.View
import com.me.base_android.nav.NavFragment
import com.me.demo.R
import kotlinx.android.synthetic.main.fragment_title.*

/**
 * Shows the main title screen with a button that navigates to [About].
 */
class Title : NavFragment() {
    override val layoutId = R.layout.fragment_title

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        about_btn.setOnClickListener {
            navigate(About())
        }

    }


    override fun onVisible() {
        super.onVisible()

        showCookieBar("title")
    }
}
