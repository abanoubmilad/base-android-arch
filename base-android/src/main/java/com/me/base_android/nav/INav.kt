package com.me.base_android.nav

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * Created by Abanoub Hanna.
 */
interface INav {

    fun navigate(tabIndex: Int, clearAllTop: Boolean = false)

    fun dismiss(clearAllTop: Boolean = false)

    fun dismissThenNavigate(fragment: Fragment, bundle: Bundle? = null)

    fun navigate(fragment: Fragment, bundle: Bundle? = null)

    fun navigate(tabIndex: Int, fragment: Fragment, bundle: Bundle? = null)

}