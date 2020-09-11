package com.me.baseAndroid.network

import android.os.Build
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 5/1/20 11:05 PM
 *  * Last modified 5/1/20 11:05 PM
 *
 */
class ApiBuilder(
    private val appVersion: String, private var token: String? = null,
    private val debug: Boolean = false,
    private val tokenPrefix: String = "Token token=",
    private val serlizeNulls: Boolean = false
) {

    val HTTP_REQUEST_TIMEOUT = 30

    /**
     * Class variables
     */

    /**
     * Network interceptors
     */
    val interceptor by lazy {
        { chain: Interceptor.Chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("User-Agent", "android")
                .addHeader("app-version", appVersion)
                .addHeader("os-version", Build.VERSION.RELEASE)
                .apply {
                    if (!token.isNullOrBlank()) {
                        addHeader("Authorization", "$tokenPrefix$token")
                    }
                }
            chain.proceed(request.build())
        }
    }

    /**
     * Client initialization
     */
    val CLIENT by lazy {
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .apply {
                if (debug)
                    addInterceptor(HttpLoggingInterceptor().apply {
                        level = (HttpLoggingInterceptor.Level.BODY)
                    })
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
        return getRXRetrofit(baseUrl).create(apiInterfaceClass)
    }

    fun getRXRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .apply {
                if (serlizeNulls)
                    addConverterFactory(
                        GsonConverterFactory.create(
                            GsonBuilder().serializeNulls().create()
                        )
                    )
                else
                    addConverterFactory(GsonConverterFactory.create())

            }
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(CLIENT)
            .build()
    }

}