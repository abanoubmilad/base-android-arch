package com.me.baseAndroid.base

import android.content.Context
import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.me.baseAndroid.R
import com.me.baseAndroid.common.ITextWatcher
import org.aviran.cookiebar2.CookieBar

/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 5/1/20 11:05 PM
 *  * Last modified 5/1/20 11:05 PM
 *
 */
abstract class BaseActivity : AppCompatActivity(),
    ITextWatcher {
    override val watchMap: HashMap<EditText, TextWatcher> by lazy {
        hashMapOf<EditText, TextWatcher>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
    }

    abstract val layoutId: Int

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun showToast(resource: Int) {
        Toast.makeText(this, resource, Toast.LENGTH_SHORT).show()
    }

    fun hideKeyboard() {
        currentFocus?.let {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    fun showKeyboard(view: View?) {
        view?.let {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }


    fun showCookieBar(
        message: String,
        bgResId: Int = R.color.base_arch_module_cookiebar_error_red
    ) {
        CookieBar.build(this)
            .setCustomView(R.layout.base_arch_module_layout_cookiebar)
            .setDuration(3000)
            .setEnableAutoDismiss(true)
            .setSwipeToDismiss(false)
            .setCookiePosition(CookieBar.TOP)
            .setBackgroundColor(bgResId)
            .setMessageColor(R.color.base_arch_module_cookiebar_text_color)
            .setMessage(message)
            .show()

    }

    fun showCookieBar(
        highLightFunction: (TextView) -> Unit,
        bgResId: Int = R.color.base_arch_module_cookiebar_error_red,
        duration: Long = 3000
    ) {
        CookieBar.build(this)
            .setCustomView(R.layout.base_arch_module_layout_cookiebar)
            .setDuration(duration)
            .setEnableAutoDismiss(true)
            .setSwipeToDismiss(false)
            .setCookiePosition(CookieBar.TOP)
            .setBackgroundColor(bgResId)
            .setMessageColor(R.color.base_arch_module_cookiebar_text_color)
            .setCustomViewInitializer { view ->
                highLightFunction(view.findViewById(R.id.tv_message))
            }
            .show()

    }


    fun showCookieBar(
        messageResId: Int,
        bgResId: Int = R.color.base_arch_module_cookiebar_error_red
    ) {
        showCookieBar(getString(messageResId), bgResId)

    }

    fun hideCookieBar() {
        CookieBar.dismiss(this)
    }

    open fun listenToMessagesOf(viewModel: BaseViewModel) {
        viewModel.info.observe(this, Observer {
            showCookieBar(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeITextWatcher()
    }
}