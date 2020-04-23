package com.me.demo.listscreen

import android.os.Bundle
import android.view.View
import com.me.base_android.nav.NavFragment
import com.me.demo.R
import com.me.demo.listscreen.MyAdapter.Companion.USERNAME_KEY
import kotlinx.android.synthetic.main.fragment_user_profile.*


/**
 * Shows a profile screen for a user, taking the name from the arguments.
 */
class UserProfile : NavFragment() {
    override val layoutId = R.layout.fragment_user_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = arguments?.getString(USERNAME_KEY) ?: "Ali Connors"
        profile_user_name.text = name
    }


    override fun onVisible() {
        super.onVisible()

        showCookieBar("User profile")
    }
}
