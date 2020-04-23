package com.me.base_android.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.me.base_android.R

/**
 * Created by Abanoub Hanna.
 */
open class InputText(context: Context, attrs: AttributeSet) : EditText(context, attrs) {
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
