package com.me.baseAndroid.base

import android.view.View
import androidx.annotation.IdRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.abanoubmilad.nut.SingleLiveEvent

/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 5/1/20 11:05 PM
 *  * Last modified 5/1/20 11:05 PM
 *
 */
open class BaseViewModel : org.abanoubmilad.nut.BaseViewModel() {

    private val _message =
        SingleLiveEvent<@IdRes Int>()
    val info: LiveData<Int> = _message

    private val _loading =
        MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading

    fun showLoading(show: Boolean) {
        _loading.postValue(show)
    }

    fun showLoading() {
        _loading.postValue(true)
    }

    fun hideLoading() {
        _loading.postValue(false)
    }

    fun fireMessage(@IdRes msg: Int) {
        _message.postValue(msg)
    }

    fun visibleOrInvisible(visible: Boolean): Int {
        return if (visible) View.VISIBLE else View.INVISIBLE
    }

    fun visibleOrGone(visible: Boolean): Int {
        return if (visible) View.VISIBLE else View.GONE
    }

}