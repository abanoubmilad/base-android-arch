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

/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 5/1/20 11:05 PM
 *  * Last modified 5/1/20 11:05 PM
 *
 */
interface Isync {

    companion object {
        val gson = Gson()
    }

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
        finally: (() -> Unit)? = null,
        observeOnUiThread: Boolean = false
    ) {

        disposable.add(
            call
                .subscribeOn(Schedulers.io())
                .observeOn(if (observeOnUiThread) AndroidSchedulers.mainThread() else Schedulers.io())
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
                    firstCall.toObservable().subscribeOn(Schedulers.io()),
                    secondCall.toObservable().subscribeOn(Schedulers.io()),
                    BiFunction<R, T, Pair<R, T>> { firstResponse, SecondResponse ->
                        // here we get both the results at a time.
                        return@BiFunction Pair(firstResponse, SecondResponse)
                    })
                .observeOn(Schedulers.io())
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

    fun <R, T> makeNetworkRequestsSequential(
        firstCall: Single<Response<R>>,
        secondCall: (R?) -> Single<Response<T>>,
        onSuccess: (T?) -> Unit,
        onFailure: (NetworkResponseStatus) -> Unit,
        finally: (() -> Unit)? = null
    ) {

        disposable.add(
            firstCall
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({ firstResponse ->
                    handleNetworkResponse(firstResponse, { response ->
                        disposable.add(
                            secondCall.invoke(response)
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe({ secondResponse ->
                                    handleNetworkResponse(secondResponse, onSuccess, onFailure)
                                    finally?.invoke()
                                }, {
                                    handleNetworkFailure(it, onFailure)
                                    finally?.invoke()
                                })
                        )
                    }, { networkResponseStatus ->
                        onFailure.invoke(networkResponseStatus)
                        finally?.invoke()
                    })
                }, {
                    handleNetworkFailure(it, onFailure)
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
                    gson.fromJson(
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
        finally: (() -> Unit)? = null,
        observeOnUiThread: Boolean = false
    ) {
        make(call,
            {
                handleNetworkResponse(it, onSuccess, onFailure)
            }, {
                handleNetworkFailure(it, onFailure)
            }, finally,
            observeOnUiThread
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