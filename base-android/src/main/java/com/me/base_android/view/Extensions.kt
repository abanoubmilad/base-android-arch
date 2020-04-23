package com.me.base_android.view

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import java.text.NumberFormat
import java.util.*
import kotlin.math.roundToInt

/**
 * Created by Abanoub Hanna.
 */
fun Double?.getPercentageAsString(context: Context?): String? {
    return if (this != null && context != null) "%1\$.0f%%".format(this * 100) else null
}

fun Int?.getCurrencyAsString(): String? {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    formatter.minimumFractionDigits = 0
    formatter.maximumFractionDigits = 2
    return if (this != null) formatter.format(this) else null
}

fun Int?.getNumberAsString(): String? {
    val formatter = NumberFormat.getNumberInstance(Locale.US)
    formatter.minimumFractionDigits = 0
    formatter.maximumFractionDigits = 2
    return if (this != null) formatter.format(this) else null
}

fun Context?.activity(): Activity? = when (this) {
    is Activity -> this
    else -> (this as? ContextWrapper)?.baseContext?.activity()
}


fun Int.convertPixelsToDp(): Int {
    val metrics = Resources.getSystem().displayMetrics
    val dp = this / (metrics.densityDpi / 160f)
    return dp.roundToInt()
}

fun Int.convertDpToPixel(): Int {
    val metrics = Resources.getSystem().displayMetrics
    val px = this * (metrics.densityDpi / 160f)
    return px.roundToInt()
}