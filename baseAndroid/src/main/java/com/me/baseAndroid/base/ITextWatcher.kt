package com.me.baseAndroid.base

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 5/1/20 11:05 PM
 *  * Last modified 5/1/20 11:05 PM
 *
 */
interface ITextWatcher {
    val watchMap: HashMap<EditText, TextWatcher>


    /**
     * function to simplify setting an afterTextChanged action to EditText components.
     */
    fun afterTextChanged(editText: EditText, callback: (String) -> Unit) {
        val watchCallback = object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                callback.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        }
        editText.addTextChangedListener(watchCallback)
        watchMap[editText] = watchCallback
    }

    fun disposeITextWatcher() {
        for ((editText, callback) in watchMap) {
            editText.removeTextChangedListener(callback)
        }
    }

    fun restoreITextWatcher() {
        for ((editText, callback) in watchMap) {
            editText.addTextChangedListener(callback)
        }
    }
}