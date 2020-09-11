package com.me.demo.formscreen

import android.widget.Toast
import com.me.baseAndroid.nav.NavFragment
import com.me.baseAndroid.view.showDialogSingleChoice
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

        about_dialog.setOnClickListener {
            showEducationLevelDialog()
        }
    }


    private fun showEducationLevelDialog() {
        val items = resources.getStringArray(R.array.array_education_levels)
        val selected = 2
        activity?.showDialogSingleChoice("select edu level", items, selected) {
            if (it < items.size) {
                Toast.makeText(activity, items[it], Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onVisible() {

        showCookieBar("Register")
    }

}
