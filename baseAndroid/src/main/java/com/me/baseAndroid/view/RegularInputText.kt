/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 5/31/20 5:36 PM
 *  * Last modified 5/1/20 11:06 PM
 *
 */

package com.me.baseAndroid.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import com.me.baseAndroid.R

/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 5/1/20 11:05 PM
 *  * Last modified 5/1/20 11:05 PM
 *
 */
open class RegularInputText(context: Context, attrs: AttributeSet) : EditText(context, attrs) {
    private val STATE_ERROR = intArrayOf(R.attr.state_error)
    var hasError = false
        set(value) {
            field = value
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
