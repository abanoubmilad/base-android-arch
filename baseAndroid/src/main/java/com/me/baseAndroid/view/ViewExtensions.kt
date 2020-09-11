package com.me.baseAndroid.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.me.baseAndroid.base.BaseViewModel


/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 5/1/20 11:05 PM
 *  * Last modified 5/1/20 11:05 PM
 *
 */
fun <V : BaseViewModel> Fragment.getPrivateViewModel(clazz: Class<V>): V {
    return ViewModelProvider(this).get(clazz)
}

fun <V : BaseViewModel> Fragment.getSharedViewModel(clazz: Class<V>): V {
    return activity?.getViewModel(clazz) ?: throw RuntimeException("Error initializing ViewModel")
}

fun <V : BaseViewModel> FragmentActivity.getViewModel(clazz: Class<V>): V {
    return this.run {
        ViewModelProvider(this).get(clazz)
    }
}

fun <T> FragmentActivity.observe(liveData: LiveData<T>, callback: (T) -> Unit) {
    liveData.observe(this, Observer(callback))
}

fun <T> FragmentActivity.observe(liveData: MutableLiveData<T>, callback: (T) -> Unit) {
    liveData.observe(this, Observer(callback))
}

fun <T> Fragment.observe(liveData: MutableLiveData<T>, callback: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer(callback))
}

fun <T> Fragment.observe(liveData: LiveData<T>, callback: (T) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer(callback))
}

fun View?.makeGone() {
    this?.visibility = View.GONE
}

fun View?.makeVisible() {
    this?.visibility = View.VISIBLE
}

fun View?.makeInvisible() {
    this?.visibility = View.INVISIBLE
}

fun TextView.highLightKeyword(
    text: String,
    keywords: List<String>,
    makeBold: Boolean = true,
    color: Int? = null,
    onClicks: List<() -> Unit>? = null,
    isUnderLine: Boolean = false,
    ignoreCase: Boolean = true,
    linkColor: Int = Color.BLUE
) {
    val sb = SpannableString(text)
    var hasSpan = false
    var onClickCounter = 0

    for (keyword in keywords) {
        val index = text.indexOf(keyword, 0, ignoreCase)
        if (index != -1) {
            hasSpan = true
            if (makeBold)
                sb.setSpan(
                    StyleSpan(android.graphics.Typeface.BOLD), index,
                    index + keyword.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            if (color != null)
                sb.setSpan(
                    ForegroundColorSpan(color), index,
                    index + keyword.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            if (onClicks != null) {
                sb.setSpan(
                    object : ClickableSpan() {
                        val current = onClickCounter++
                        override fun onClick(widget: View) {
                            onClicks.getOrNull(current)?.invoke()
                        }

                        override fun updateDrawState(ds: TextPaint) {
                            super.updateDrawState(ds)
                            ds.isUnderlineText = isUnderLine
                            ds.color = linkColor

                        }
                    }, index,
                    index + keyword.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
    }
    if (hasSpan) {
        setText(sb, TextView.BufferType.SPANNABLE)
    } else {
        setText(text)
    }
    if (onClicks != null) {
        movementMethod = LinkMovementMethod.getInstance()
        isClickable = true
    }

}

fun TextView.highLightKeyword(
    text: String,
    keyword: String,
    makeBold: Boolean = true,
    color: Int? = null,
    onClick: (() -> Unit)? = null,
    isUnderLine: Boolean = false,
    ignoreCase: Boolean = true,
    linkColor: Int = Color.BLUE
) {
    val index = text.indexOf(keyword, 0, ignoreCase)
    if (index != -1) {
        val sb = SpannableString(text)

        if (makeBold)
            sb.setSpan(
                StyleSpan(android.graphics.Typeface.BOLD), index,
                index + keyword.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        if (color != null)
            sb.setSpan(
                ForegroundColorSpan(color), index,
                index + keyword.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        if (onClick != null)
            sb.setSpan(
                object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        onClick()
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isUnderlineText = isUnderLine
                        ds.color = linkColor

                    }
                }, index,
                index + keyword.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        setText(sb)
    } else {
        setText(text)
    }
    if (onClick != null) {
        movementMethod = LinkMovementMethod.getInstance()
        isClickable = true
    }

}

fun FragmentActivity?.makeStatusBarFullyTransperant() {
    this?.window?.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
}

fun FragmentActivity?.startUrlIntent(url: String?) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        this?.startActivity(intent)
    } catch (e: Exception) {
    }

}

fun FragmentActivity.showDialog(
    title: String? = null,
    items: Array<String>,
    onItemSelected: (Int) -> Unit
) {
    var alertDialog: AlertDialog? = null
    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
    builder.setItems(items) { dialog, which ->
        onItemSelected(which)
        dialog.dismiss()
    }
    if (title != null)
        builder.setTitle(title)
    alertDialog = builder.create()
    alertDialog.show()
}


fun FragmentActivity.showDialogSingleChoice(
    title: String? = null,
    itemsArrayResourceId: Int,
    selectedIndex: Int = -1,
    onItemSelected: (Int) -> Unit
) {
    var alertDialog: AlertDialog? = null
    val builder: AlertDialog.Builder = AlertDialog.Builder(this)

    builder.setSingleChoiceItems(itemsArrayResourceId, selectedIndex) { dialog, item ->
        onItemSelected(item)
        dialog.dismiss()
    }

    if (title != null)
        builder.setTitle(title)
    alertDialog = builder.create()
    alertDialog.show()
}

fun FragmentActivity.showDialogSingleChoice(
    title: String? = null,
    items: Array<String>,
    selectedIndex: Int = -1,
    onItemSelected: (Int) -> Unit
) {
    var alertDialog: AlertDialog? = null
    val builder: AlertDialog.Builder = AlertDialog.Builder(this)

    builder.setSingleChoiceItems(items, selectedIndex) { dialog, item ->
        onItemSelected(item)
        dialog.dismiss()
    }

    if (title != null)
        builder.setTitle(title)
    alertDialog = builder.create()
    alertDialog.show()
}

@Suppress("DEPRECATION")
fun Context?.isInternetAvailable(): Boolean {
    var result = false
    val cm = this?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        cm?.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        }
    } else {
        cm?.run {
            cm.activeNetworkInfo?.run {
                if (type == ConnectivityManager.TYPE_WIFI) {
                    result = true
                } else if (type == ConnectivityManager.TYPE_MOBILE) {
                    result = true
                }
            }
        }
    }
    return result
}


fun Fragment?.executeIfAdded(toExec: (fragment: Fragment) -> Unit) {
    if (this?.isAdded == true) {
        try {
            toExec(this)
        } catch (e: IllegalStateException) {
        }
    }
}

fun EditText.setOnDoneButtonClicked(callback: (String) -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        when (actionId) {
            EditorInfo.IME_ACTION_DONE ->
                callback.invoke(text.toString())
        }
        false
    }
}

fun EditText.setOnLostFocus(callback: (String) -> Unit) {
    this.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        if (!hasFocus) {
            callback.invoke(text.toString())
        }
    }
}

fun EditText.setOnEditingFinished(callback: (String) -> Unit) {
    setOnDoneButtonClicked(callback)
    setOnLostFocus(callback)
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

fun TextView.setTextOrGone(str: String?, vararg others: View) {
    if (str.isNullOrBlank()) {
        makeGone()
        others.forEach { it.makeGone() }
    } else {
        text = str
        makeVisible()
        others.forEach { it.makeVisible() }
    }
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
