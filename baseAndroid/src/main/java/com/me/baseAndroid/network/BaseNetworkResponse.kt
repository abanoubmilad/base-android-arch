package com.me.baseAndroid.network

import com.google.gson.annotations.SerializedName


/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 5/1/20 11:05 PM
 *  * Last modified 5/1/20 11:05 PM
 *
 */

open class BaseNetworkResponse<T> : NetworkResponseStatus() {
    @SerializedName("data")
    var data: T? = null
}