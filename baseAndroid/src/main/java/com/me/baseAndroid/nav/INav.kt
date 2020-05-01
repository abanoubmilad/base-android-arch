package com.me.baseAndroid.nav

import android.os.Bundle
import androidx.fragment.app.Fragment

/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 5/1/20 11:05 PM
 *  * Last modified 5/1/20 11:05 PM
 *
 */
interface INav {

    fun navigate(tabIndex: Int, clearAllTop: Boolean = false)

    fun dismiss(clearAllTop: Boolean = false)

    fun dismissThenNavigate(fragment: Fragment, bundle: Bundle? = null)

    fun navigate(fragment: Fragment, bundle: Bundle? = null)

    fun navigate(tabIndex: Int, fragment: Fragment, bundle: Bundle? = null)

    fun getCurrentFragment(): Fragment?
}