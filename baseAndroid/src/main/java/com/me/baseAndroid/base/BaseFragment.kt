package com.me.baseAndroid.base

import android.content.Context
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.me.baseAndroid.R
import com.me.baseAndroid.common.ITextWatcher
import org.abanoubmilad.labyrinth.NavFragment

/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 5/1/20 11:05 PM
 *  * Last modified 5/1/20 11:05 PM
 *
 */
abstract class BaseFragment : NavFragment(),
    ITextWatcher {
    override val watchMap: HashMap<EditText, TextWatcher> by lazy {
        hashMapOf<EditText, TextWatcher>()
    }

    fun showToast(msg: String) {
        activity?.let {
            Toast.makeText(it, msg, Toast.LENGTH_SHORT).show()
        }
    }

    fun showToast(resource: Int) {
        activity?.let {
            Toast.makeText(it, resource, Toast.LENGTH_SHORT).show()
        }
    }

    fun hideCookieBar() {
        (activity as? BaseActivity)?.let {
            it.hideCookieBar()
        }
    }

    fun showCookieBar(resource: Int) {
        (activity as? BaseActivity)?.let {
            it.showCookieBar(resource)
        }
    }

    fun showCookieBar(msg: String) {
        (activity as? BaseActivity)?.let {
            it.showCookieBar(msg)
        }
    }

    fun showCookieBar(
        highLightFunction: (TextView) -> Unit,
        bgResId: Int = R.color.base_arch_module_cookiebar_error_red,
        duration: Long = 3000
    ) {
        (activity as? BaseActivity)?.let {
            it.showCookieBar(highLightFunction, bgResId, duration)
        }
    }

    fun hideKeyboard() {
        activity?.let { parent ->
            parent.currentFocus?.let {
                (parent.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(it.windowToken, 0)
            }

        }
    }

    fun showKeyboard(view: View?) {
        activity?.let { parent ->
            (parent.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    open fun listenToMessagesOf(viewModel: BaseViewModel) {
        viewModel.info.observe(viewLifecycleOwner, Observer {
            showCookieBar(it)
        })
    }


    override fun onDestroyView() {
        disposeITextWatcher()
        super.onDestroyView()
    }


}