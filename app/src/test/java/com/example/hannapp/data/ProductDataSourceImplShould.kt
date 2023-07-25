package com.example.hannapp.data

import com.example.hannapp.data.model.api.Nutriments
import com.example.hannapp.data.model.api.Product
import com.example.hannapp.data.model.api.ProductSearchResult
import com.example.hannapp.data.remote.ProductSearchApi
import com.example.hannapp.data.source.ProductDataSourceImpl
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class ProductDataSourceImplShould {

    private lateinit var productDataSourceImpl: ProductDataSourceImpl
    private val productSearchApi = mock<ProductSearchApi>()

    private val products = listOf(
        Product(
            code = "12345",
            productName = "Apple",
            nutriments = Nutriments(
                kcal = 1.0,
                protein = 1.0,
                fat = 1.0,
                carbohydrates = 1.0,
                sugar = 1.0,
                fiber = 1.0,
                alcohol = 0.0
            )
        )
    )

    private val productSearchResult = ProductSearchResult(
        count = 2,
        page = 1,
        pageCount = 1,
        pageSize = 24,
        skip = 0,
        products = products
    )

    @BeforeEach
    fun beforeEach() {
        productDataSourceImpl = ProductDataSourceImpl(
            productSearchApi = productSearchApi
        )
    }

    @Test
    fun returnResultOnSuccess() = runTest {
        whenever(productSearchApi.search(any(), any(), any(), any(), any())).thenReturn(
            Response.success(200, productSearchResult)
        )

        val result = productDataSourceImpl.search(
            searchString = "Apple",
            page = 1,
            pageSize = 24
        )

        assertThat(result).isEqualTo(NetworkResult.Success(products))
    }

    @Test
    fun returnErrorOnNetworkError() = runTest {
        val message = "Internal Server Error"

        whenever(productSearchApi.search(any(), any(), any(), any(), any())).thenReturn(
            Response.error(500, message.toResponseBody())
        )

        val result = productDataSourceImpl.search(
            searchString = "Apple",
            page = 1,
            pageSize = 24
        )

        assertThat(result).isEqualTo(
            NetworkResult.Error<List<Product>>(
                code = 500,
                message = message
            )
        )
    }

    @Test
    fun returnErrorOnException() = runTest {
        val exception = RuntimeException("exception")

        whenever(productSearchApi.search(any(), any(), any(), any(), any())).thenThrow(
            exception
        )

        val result = productDataSourceImpl.search(
            searchString = "Apple",
            page = 1,
            pageSize = 24
        )

        assertThat(result).isEqualTo(NetworkResult.Exception<List<Product>>(exception))
    }
}
