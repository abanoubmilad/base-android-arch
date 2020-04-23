package com.me.demo.formscreen

import android.os.Bundle
import android.view.View
import com.me.base_android.nav.NavFragment
import com.me.demo.R
import com.me.demo.homescreen.Title
import kotlinx.android.synthetic.main.fragment_register.*


/**
 * Shows a register form to showcase UI state persistence. It has a button that goes to [Registered]
 */
class Register3 : NavFragment() {
    override val layoutId = R.layout.fragment_register3

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signup_btn.setOnClickListener {
            navigate(Registered())
        }
        about_btn.setOnClickListener {
            navigate(0, Title())

        }
    }


    override fun onVisible() {
        super.onVisible()

        showCookieBar("Register 3")
    }

}
