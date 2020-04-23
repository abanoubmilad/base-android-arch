package com.me.base_android.network

import com.google.gson.annotations.SerializedName


/**
 * Created by Abanoub Hanna on 2019-05-28.
 */

open class BaseNetworkResponse<T> : NetworkResponseStatus() {
    @SerializedName("data")
    var data: T? = null
}