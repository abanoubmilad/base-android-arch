package com.me.baseAndroid.base

import android.os.Bundle
import com.me.baseAndroid.R
import com.me.baseAndroid.view.isInternetAvailable
import com.novoda.merlin.Merlin

/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 5/1/20 11:05 PM
 *  * Last modified 5/1/20 11:05 PM
 *
 */
abstract class ConnectifyActivity : BaseActivity() {

    private lateinit var merlin: Merlin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        merlin = Merlin.Builder().withConnectableCallbacks()
            .withDisconnectableCallbacks().build(this)
    }

    override fun onResume() {
        super.onResume()
        if (!isInternetAvailable()) {
            onDisconnected()
        }
        merlin.registerConnectable {
            runOnUiThread {
                onConnected()
            }
        }
        merlin.registerDisconnectable {
            runOnUiThread {
                onDisconnected()
            }
        }
        merlin.bind()
    }


    abstract fun onDisconnected()
    abstract fun onConnected()

    override fun onPause() {
        merlin.unbind()
        super.onPause()
    }

    open fun showDisconnectedAlert() {
        showCookieBar(
            R.string.base_arch_module_check_connection,
            R.color.base_arch_module_cookiebar_error_grey
        )
    }
}