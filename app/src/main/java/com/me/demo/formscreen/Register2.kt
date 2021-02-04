package com.me.demo.formscreen

import com.me.demo.R
import com.me.demo.homescreen.Title


/**
 * Shows a register form to showcase UI state persistence. It has a button that goes to [Registered]
 */
class Register2 : NavFragment() {
    override val layoutId = R.layout.fragment_register2

    override fun onCreated() {

        signup_btn.setOnClickListener {
            INav?.navigate(Register3())
        }
        about_btn.setOnClickListener {
            INav?.navigate(0, Title())

        }
    }

    override fun onVisible() {

        showCookieBar("Register 2")
    }

}

fun NavFragment.showSnackBar(msg: String) {
    view?.let {
        Snackbar.make(it, msg, Snackbar.LENGTH_SHORT)
            .setActionTextColor(ContextCompat.getColor(it.context, R.color.colorAccent))
            .show()
    }

}
