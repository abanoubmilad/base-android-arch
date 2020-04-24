package com.me.baseAndroid.base

import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.me.baseAndroid.network.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Abanoub Hanna.
 */
open class BaseViewModel : ViewModel(), Isync, IObserve {
    override val disposable = CompositeDisposable()

    override val observablesMap by lazy {
        hashMapOf<ObservableField<*>, Observable.OnPropertyChangedCallback>()
    }


    private val _message =
        SingleLiveEvent<@IdRes Int>()
    val info: LiveData<Int> = _message

    private val _loading =
        MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading

    fun showLoading(show: Boolean) {
        _loading.postValue(show)
    }

    fun showLoading() {
        _loading.postValue(true)
    }

    fun hideLoading() {
        _loading.postValue(false)
    }

    fun fireMessage(@IdRes msg: Int) {
        _message.postValue(msg)
    }

    fun visibleOrInvisible(visible: Boolean): Int {
        return if (visible) View.VISIBLE else View.INVISIBLE
    }

    fun visibleOrGone(visible: Boolean): Int {
        return if (visible) View.VISIBLE else View.GONE
    }

    @CallSuper
    override fun onCleared() {
        disposeIsync()
        disposeIObserve()
    }


}