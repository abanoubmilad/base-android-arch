package com.me.demo.formscreen

import com.me.baseAndroid.nav.NavFragment
import com.me.demo.R
import com.me.demo.homescreen.About
import kotlinx.android.synthetic.main.fragment_register.*


/**
 * Shows a register form to showcase UI state persistence. It has a button that goes to [Registered]
 */
class Register : NavFragment() {
    override val layoutId = R.layout.fragment_register

    override fun onCreated() {

        signup_btn.setOnClickListener {
            INav?.navigate(Register2())
        }
        about_btn.setOnClickListener {
            INav?.navigate(0, About())

        }

        about_btn_clear_top.setOnClickListener {

        }
    }


    override fun onVisibleAgain() {
        super.onVisibleAgain()

        showCookieBar("Register")
    }

}
