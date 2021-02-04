/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 2/4/21 6:20 AM
 *  * Last modified 2/4/21 6:18 AM
 *
 */

package com.me.baseAndroid.extentions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView


fun View?.makeGone() {
    this?.visibility = View.GONE
}

fun View?.makeVisible() {
    this?.visibility = View.VISIBLE
}

fun View?.makeInvisible() {
    this?.visibility = View.INVISIBLE
}

fun FragmentActivity?.makeStatusBarFullyTransparent() {
    this?.window?.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
}


fun Fragment?.executeIfAdded(toExec: (fragment: Fragment) -> Unit) {
    if (this?.isAdded == true) {
        try {
            toExec(this)
        } catch (e: IllegalStateException) {
        }
    }
}


fun RecyclerView.onScrollTop(callback: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (recyclerView.canScrollVertically(-1))
                callback.invoke()
        }
    })
}

fun RecyclerView.onScrollBottom(callback: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (recyclerView.canScrollVertically(1))
                callback.invoke()
        }
    })
}


fun Context?.inflateLayout(resId: Int): View? {
    return LayoutInflater.from(this).inflate(
        resId, null
    )
}

fun Fragment.inflateLayout(resId: Int): View? {
    return context.inflateLayout(resId)
}

fun FragmentActivity.inflateLayout(resId: Int): View? {
    return inflateLayout(resId)
}

fun visibleCountOf(views: Sequence<View>): Int {
    var count = 0
    views.forEach { if (it.isVisible) count++ }
    return count
}

fun Context.getContextCompatDrawable(@DrawableRes resId: Int) =
    ContextCompat.getDrawable(this, resId)

fun Fragment.getContextCompatDrawable(@DrawableRes resId: Int) =
    activity?.getContextCompatDrawable(resId)
