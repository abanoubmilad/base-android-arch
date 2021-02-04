/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 2/4/21 6:18 AM
 *  * Last modified 2/4/21 6:18 AM
 *
 */

package com.me.baseAndroid.extentions

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView


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
                    StyleSpan(Typeface.BOLD), index,
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
                StyleSpan(Typeface.BOLD), index,
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
