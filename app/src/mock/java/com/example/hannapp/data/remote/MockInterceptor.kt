package com.example.hannapp.data.remote

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class MockInterceptor(
    private val mockResponse: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(
        chain.request()
    ).newBuilder()
        .code(200)
        .message(mockResponse)
        .body(mockResponse.toResponseBody("application/json".toMediaTypeOrNull()))
        .addHeader("content-type", "application/json")
        .build()
}
