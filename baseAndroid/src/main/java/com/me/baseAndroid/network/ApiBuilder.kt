package com.me.baseAndroid.network

import android.os.Build
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Abanoub Hanna.
 */
class ApiBuilder(
    private val appVersion: String, private var token: String? = null,
    private val debug: Boolean = false
) {

    val HTTP_REQUEST_TIMEOUT = 30

    /**
     * Class variables
     */

    /**
     * Network interceptors
     */
    private val interceptor by lazy {
        { chain: Interceptor.Chain ->
            val request = chain.request()
                .newBuilder()
            request.addHeader("Accept", "application/json")
                .addHeader("User-Agent", "android")
                .addHeader("app-version", appVersion)
                .addHeader("os-version", Build.VERSION.RELEASE)
                .apply {
                    if (!token.isNullOrBlank()) {
                        addHeader("Authorization", "Token token=$token")
                    }
                }
                .build()
            val response = chain.proceed(request.build())

            response
        }
    }
    /**
     * Client initialization
     */
    private val CLIENT by lazy {
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .apply {
                if (debug)
                    addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            }
            .connectTimeout(HTTP_REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(HTTP_REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(HTTP_REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .build()
    }

    /**
     * Instances for network builder and retrofit initialization
     */

    fun <S> getRXApiInstance(apiInterfaceClass: Class<S>, baseUrl: String): S {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(CLIENT)
            .build()
            .create(apiInterfaceClass)
    }


}