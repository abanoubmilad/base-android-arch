package com.me.baseAndroid.view

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import java.text.NumberFormat
import java.util.*

/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 5/1/20 11:05 PM
 *  * Last modified 5/1/20 11:05 PM
 *
 */

fun Int?.toCurrencyString(
    minimumFractionDigits: Int = 0,
    maximumFractionDigits: Int = 2,
    inLocale: Locale = Locale.US
): String? {
    val formatter = NumberFormat.getCurrencyInstance(inLocale)
    formatter.minimumFractionDigits = minimumFractionDigits
    formatter.maximumFractionDigits = maximumFractionDigits
    return if (this != null) formatter.format(this) else null
}

fun Int?.toNumberAsString(
    minimumFractionDigits: Int = 0,
    maximumFractionDigits: Int = 2,
    inLocale: Locale = Locale.US
): String? {
    val formatter = NumberFormat.getNumberInstance(inLocale)
    formatter.minimumFractionDigits = minimumFractionDigits
    formatter.maximumFractionDigits = maximumFractionDigits
    return if (this != null) formatter.format(this) else null
}

fun Context?.activity(): Activity? = when (this) {
    is Activity -> this
    else -> (this as? ContextWrapper)?.baseContext?.activity()
}
