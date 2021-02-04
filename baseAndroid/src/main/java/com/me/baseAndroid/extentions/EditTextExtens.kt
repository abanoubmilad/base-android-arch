/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 2/4/21 6:18 AM
 *  * Last modified 2/4/21 6:18 AM
 *
 */

package com.me.baseAndroid.extentions

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText


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