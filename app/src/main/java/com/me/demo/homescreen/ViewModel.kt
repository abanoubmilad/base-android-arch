package com.me.demo.homescreen

import com.me.baseAndroid.base.BaseViewModel

class ViewModel : BaseViewModel() {

    var check = true


    fun checkMe() {
        check = false
    }

    override fun onCleared() {
        super.onCleared()
    }
}