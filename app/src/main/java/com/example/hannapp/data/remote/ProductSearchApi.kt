package com.example.hannapp.data.remote

import com.example.hannapp.data.model.api.ProductSearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductSearchApi {
    @GET("search.pl")
    suspend fun search(
        @Query("search_terms") searchString: String,
        @Query("page") page: Int,
        @Query("json") json: Int = 1,
        @Query("fields") fields: String = "code,product_name,nutriments,image_front_thumb_url",
        @Query("page_size") pageSize: Int = 24
    ): Response<ProductSearchResult>
}
