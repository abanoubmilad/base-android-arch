package com.me.base_android.base

import android.os.Bundle
import com.me.base_android.R
import com.me.base_android.view.isInternetAvailable
import com.novoda.merlin.Merlin

/**
 * Created by Abanoub Hanna.
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