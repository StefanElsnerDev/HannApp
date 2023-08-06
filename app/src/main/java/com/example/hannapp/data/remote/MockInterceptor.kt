package com.example.hannapp.data.remote

import com.example.hannapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class MockInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (BuildConfig.BUILD_TYPE == "mock") {
            chain.proceed(
                chain.request()
            ).newBuilder()
                .code(200)
                .message(mockResponse)
                .body(mockResponse.toResponseBody("application/json".toMediaTypeOrNull()))
                .addHeader("content-type", "application/json")
                .build()
        } else {
            chain.proceed(
                chain.request()
            )
        }
    }
}

const val mockResponse =
    """{"count":30,"page":1,"page_count":24,"page_size":24,"products":[{"code":"112","nutriments":{"carbohydrates":1,"carbohydrates_100g":1,"carbohydrates_unit":"g","carbohydrates_value":1,"energy":1294,"energy-kj":1294,"energy-kj_100g":1294,"energy-kj_unit":"kJ","energy-kj_value":1294,"energy-kj_value_computed":1282,"energy_100g":1294,"energy_unit":"kJ","energy_value":1294,"fat":25,"fat_100g":25,"fat_unit":"g","fat_value":25,"nutrition-score-fr":15,"nutrition-score-fr_100g":15,"proteins":20,"proteins_100g":20,"proteins_unit":"g","proteins_value":20,"salt":1.8,"salt_100g":1.8,"salt_unit":"g","salt_value":1.8,"saturated-fat":18,"saturated-fat_100g":18,"saturated-fat_unit":"g","saturated-fat_value":18,"sodium":0.72,"sodium_100g":0.72,"sodium_unit":"g","sodium_value":0.72,"sugars":1,"sugars_100g":1,"sugars_unit":"g","sugars_value":1},"product_name":"Franz Beyer"}],"skip":0}"""
