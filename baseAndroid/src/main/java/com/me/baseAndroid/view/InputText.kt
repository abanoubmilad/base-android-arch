package com.me.baseAndroid.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.me.baseAndroid.R

/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 5/1/20 11:05 PM
 *  * Last modified 5/1/20 11:05 PM
 *
 */
open class InputText(context: Context, attrs: AttributeSet) :
    androidx.appcompat.widget.AppCompatEditText(context, attrs) {
    private val STATE_ERROR = intArrayOf(R.attr.state_error)
    var hasError = false
        set(value) {
            field = value
            typeface = if (value) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
            setHintTextColor(
                ContextCompat.getColor(
                    context,
                    if (value) R.color.base_arch_module_input_text_error_text_color
                    else R.color.base_arch_module_input_text_text_color
                )
            )
            refreshDrawableState()
        }

    override fun onCreateDrawableState(extraSpace: Int): IntArray? {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (hasError) {
            View.mergeDrawableStates(drawableState, STATE_ERROR)
        }
        return drawableState
    }

}
