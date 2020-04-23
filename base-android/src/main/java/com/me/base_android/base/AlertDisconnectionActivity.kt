package com.me.base_android.base

/**
 * Created by Abanoub Hanna.
 */
abstract class AlertDisconnectionActivity : ConnectifyActivity() {

    override fun onDisconnected() {
        showDisconnectedAlert()
    }

    override fun onConnected() {

    }

}