package com.example.hannapp.data.source

import com.example.hannapp.data.NetworkResult
import com.example.hannapp.data.model.api.Product
import com.example.hannapp.data.remote.ProductSearchApi
import javax.inject.Inject

interface ProductDataSource {
    suspend fun search(searchString: String, page: Int, pageSize: Int): NetworkResult<List<Product>>
}

class ProductDataSourceImpl @Inject constructor(
    private val productSearchApi: ProductSearchApi
) : ProductDataSource {

    override suspend fun search(
        searchString: String,
        page: Int,
        pageSize: Int
    ): NetworkResult<List<Product>> {
        try {
            val response = productSearchApi.search(
                searchString = searchString,
                page = page,
                pageSize = pageSize
            )
            val body = response.body()

            return if (response.isSuccessful && body != null) {
                NetworkResult.Success(body.products)
            } else {
                NetworkResult.Error(
                    code = response.code(),
                    message = response.errorBody()?.string() ?: ""
                )
            }
        } catch (e: Exception) {
            return NetworkResult.Exception(e)
        }
    }
}
