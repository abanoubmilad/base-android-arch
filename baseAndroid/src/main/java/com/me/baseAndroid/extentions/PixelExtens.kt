/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 2/4/21 6:20 AM
 *  * Last modified 2/4/21 6:20 AM
 *
 */

package com.me.baseAndroid.extentions

import android.content.res.Resources
import kotlin.math.roundToInt


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