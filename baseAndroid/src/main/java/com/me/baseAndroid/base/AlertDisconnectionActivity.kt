package com.me.baseAndroid.base

/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 5/1/20 11:05 PM
 *  * Last modified 5/1/20 11:05 PM
 *
 */
abstract class AlertDisconnectionActivity : ConnectifyActivity() {

    override fun onDisconnected() {
        showDisconnectedAlert()
    }

    override fun onConnected() {

    }

}