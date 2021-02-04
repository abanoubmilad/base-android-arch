package com.me.demo.formscreen

import com.me.demo.R
import com.me.demo.homescreen.Title


/**
 * Shows a register form to showcase UI state persistence. It has a button that goes to [Registered]
 */
class Register3 : NavFragment() {
    override val layoutId = R.layout.fragment_register3

    override fun onCreated() {

        signup_btn.setOnClickListener {
            INav?.navigate(Registered())
        }
        about_btn.setOnClickListener {
            INav?.navigate(0, Title())

        }
    }


    override fun onVisible() {

        showCookieBar("Register 3")
    }

}
