package com.me.baseAndroid.network

import com.google.gson.annotations.SerializedName


/**
 * Created by Abanoub Hanna on 2019-05-28.
 */

open class NetworkResponseStatus {
    @SerializedName("message")
    var message: String? = null
    @SerializedName("code")
    var code = 0
}