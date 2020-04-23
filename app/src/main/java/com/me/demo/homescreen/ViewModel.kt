package com.me.demo.homescreen

import com.me.base_android.base.BaseViewModel

class ViewModel : BaseViewModel() {

    var check = true


    fun checkMe() {
        check = false
    }
}