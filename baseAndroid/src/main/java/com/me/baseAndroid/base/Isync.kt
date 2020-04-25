package com.me.baseAndroid.base

import com.google.gson.Gson
import com.me.baseAndroid.AppLogger
import com.me.baseAndroid.BASE_ANDROID_TAG
import com.me.baseAndroid.network.NetworkConstants
import com.me.baseAndroid.network.NetworkResponseStatus
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

/**
 * Created by Abanoub Hanna.
 */
interface Isync {
    val disposable: CompositeDisposable

    var networkFailureCallBack: (() -> Unit)?

    fun disposeIsync() {
        networkFailureCallBack = null
        disposable.dispose()
    }


    fun <R> make(
        call: Single<R>,
        onSuccess: (R) -> Unit,
        onFailure: (Throwable) -> Unit,
        finally: (() -> Unit)? = null
    ) {

        disposable.add(
            call
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onSuccess.invoke(it)
                    finally?.invoke()
                }, {
                    onFailure.invoke(it)
                    finally?.invoke()
                })
        )

    }

    fun <R, T> makeParallel(
        firstCall: Single<R>,
        firstOnSuccess: (R) -> Unit,
        secondCall: Single<T>,
        secondOnSuccess: (T) -> Unit,
        onFailure: (Throwable) -> Unit,
        finally: (() -> Unit)? = null
    ) {

        disposable.add(
            Observable
                .zip(
                    firstCall.toObservable(),
                    secondCall.toObservable(),
                    BiFunction<R, T, Pair<R, T>> { firstResponse, SecondResponse ->
                        // here we get both the results at a time.
                        return@BiFunction Pair(firstResponse, SecondResponse)
                    })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    firstOnSuccess.invoke(it.first)
                    secondOnSuccess.invoke(it.second)
                    finally?.invoke()
                }, {
                    onFailure.invoke(it)
                    finally?.invoke()
                })
        )

    }

    private fun handleNetworkFailure(
        response: Throwable,
        onFailure: (NetworkResponseStatus) -> Unit
    ) {
        AppLogger.e(BASE_ANDROID_TAG, response.toString())

        onFailure(NetworkResponseStatus().apply {
            message = response.localizedMessage
        })
        networkFailureCallBack?.invoke()
    }

    private fun <R> handleNetworkResponse(
        response: Response<R>,
        onSuccess: (R?) -> Unit,
        onFailure: (NetworkResponseStatus) -> Unit
    ) {
        if (response.isSuccessful) {
            onSuccess(response.body())
        } else {

            try {
                val networkResponseStatus: NetworkResponseStatus =
                    Gson().fromJson(
                        response.errorBody()?.charStream(),
                        NetworkResponseStatus::class.java
                    )
                onFailure(networkResponseStatus)
                AppLogger.e(BASE_ANDROID_TAG, response.errorBody()?.charStream().toString())
            } catch (e: Exception) {
                AppLogger.e(BASE_ANDROID_TAG, e.toString())
                onFailure(NetworkResponseStatus().apply {
                    message = NetworkConstants.SOMETHING_WENT_WRONG_MESSAGE
                })
            }
        }
    }

    fun <R> makeNetworkRequest(
        call: Single<Response<R>>,
        onSuccess: (R?) -> Unit,
        onFailure: (NetworkResponseStatus) -> Unit,
        finally: (() -> Unit)? = null
    ) {
        make(call,
            {
                handleNetworkResponse(it, onSuccess, onFailure)
            }, {
                handleNetworkFailure(it, onFailure)
            }, finally
        )
    }


    fun <R, T> makeNetworkRequestsParallel(
        firstCall: Single<Response<R>>,
        firstOnSuccess: (R?) -> Unit,
        secondCall: Single<Response<T>>,
        secondOnSuccess: (T?) -> Unit,
        onFailure: (NetworkResponseStatus) -> Unit,
        finally: (() -> Unit)? = null
    ) {

        makeParallel(firstCall,
            {
                handleNetworkResponse(it, firstOnSuccess, onFailure)
            },
            secondCall,
            {
                handleNetworkResponse(it, secondOnSuccess, onFailure)
            }, {
                handleNetworkFailure(it, onFailure)
            }
            , finally
        )


    }
}