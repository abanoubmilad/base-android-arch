/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 2/4/21 6:16 AM
 *  * Last modified 2/4/21 6:16 AM
 *
 */

package com.me.baseAndroid.extentions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


fun <T> FragmentActivity.observe(liveData: LiveData<T>, callback: (T) -> Unit) {
    liveData.observe(this, Observer(callback))
}

fun <T> FragmentActivity.observe(liveData: MutableLiveData<T>, callback: (T) -> Unit) {
    liveData.observe(this, Observer(callback))
}

fun <T> Fragment.observe(liveData: MutableLiveData<T>, callback: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer(callback))
}

fun <T> Fragment.observe(liveData: LiveData<T>, callback: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer(callback))
}
