package com.example.myapplication.Retrofit

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = AuthUtils.getAuthToken(context)

        return if (token != null) {
            val newRequest = originalRequest.newBuilder()
                .header("Authorization", "$token")
                .build()
            chain.proceed(newRequest)
        } else {
            chain.proceed(originalRequest)
        }
    }
}