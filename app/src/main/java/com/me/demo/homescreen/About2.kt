package com.me.demo.homescreen

import com.me.baseAndroid.nav.NavFragment
import com.me.baseAndroid.view.getPrivateViewModel
import com.me.demo.R
import kotlinx.android.synthetic.main.fragment_about2.*

/**
 * Shows "About"
 */
class About2 : NavFragment() {
    override val layoutId = R.layout.fragment_about2

    val viewModel by lazy {
        getPrivateViewModel(ViewModel::class.java)
    }

    override fun onCreated() {

        btn.setOnClickListener {
            INav?.navigate(About3())

        }
    }

    override fun onVisible() {

        showCookieBar("about 2")
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}
