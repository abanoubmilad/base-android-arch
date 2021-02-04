package com.me.demo.listscreen

import com.me.demo.R
import com.me.demo.listscreen.MyAdapter.Companion.USERNAME_KEY


/**
 * Shows a profile screen for a user, taking the name from the arguments.
 */
class UserProfile : NavFragment() {
    override val layoutId = R.layout.fragment_user_profile

    override fun onCreated() {

        val name = arguments?.getString(USERNAME_KEY) ?: "Ali Connors"
        profile_user_name.text = name
    }


    override fun onVisible() {

        showCookieBar("User profile")
    }
}
