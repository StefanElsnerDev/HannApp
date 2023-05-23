package com.example.hannapp.data

import androidx.paging.PagingSource
import com.example.hannapp.data.model.api.Nutriments
import com.example.hannapp.data.model.api.Product
import com.example.hannapp.data.model.api.ProductSearchResult
import com.example.hannapp.data.remote.ProductSearchApi
import com.example.hannapp.data.source.ProductSearchPagingSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class ProductSearchPagingSourceShould {

    private lateinit var productSearchPagingSource: ProductSearchPagingSource
    private val productSearchApi: ProductSearchApi = mock()
    private val loadParams: PagingSource.LoadParams<Int> = mock()

    private val searchTerm = "Apple"
    private val firstPage = 1

    private val products = listOf(
        Product(
            code = "12345",
            productName = "Apple",
            nutriments = Nutriments(
                1.1,
                2.2,
                3.3,
                3.3,
                4.4,
                5.5,
                6.6
            )
        ),
        Product(
            code = "6789",
            productName = "Apple Green",
            nutriments = Nutriments(
                1.1,
                2.2,
                3.3,
                3.3,
                4.4,
                5.5,
                6.6
            )
        )
    )

    private val response: Response<ProductSearchResult> = Response.success(
        200,
        ProductSearchResult(
            count = 2,
            page = 0,
            pageCount = 2,
            pageSize = 24,
            products = products,
            skip = 0
        )
    )

    @BeforeEach
    fun beforeEach() = runTest {
        whenever(productSearchApi.search(searchTerm, firstPage)).thenReturn(response)

        productSearchPagingSource = ProductSearchPagingSource(
            productSearchApi = productSearchApi,
            searchString = "Apple",
            pageSize = 24
        )
    }

    @Test
    fun callProductSearchApi() = runTest {
        productSearchPagingSource.load(loadParams)

        verify(productSearchApi).search(any(), any(), any(), any(), any())
    }

    @Test
    fun loadProductsWithFirstPageSuccessfully() = runTest {
        val result = productSearchPagingSource.load(loadParams)

        Assertions.assertEquals(
            PagingSource.LoadResult.Page(
                data = products,
                prevKey = null,
                nextKey = 2
            ),
            result
        )
    }

    // TODO: Test multiple pages
    // TODO: Test nextKey for last page

    @Test
    fun returnErrorLoadResultOnFailure() = runTest {
        val errorResponse: Response<ProductSearchResult> =
            Response.error(401, "Authentication failed".toResponseBody())

        whenever(productSearchApi.search(searchTerm, firstPage)).thenReturn(
            errorResponse
        )

        val result = productSearchPagingSource.load(loadParams)

        assert(result is PagingSource.LoadResult.Error<Int, Product>)
    }

    @Test
    fun returnErrorLoadResultOnException() = runTest {
        val exception = RuntimeException("Something terrible happened")

        whenever(productSearchApi.search(searchTerm, firstPage)).thenThrow(
            exception
        )

        val result = productSearchPagingSource.load(loadParams)

        Assertions.assertEquals(
            PagingSource.LoadResult.Error<Int, Product>(
                exception
            ),
            result
        )
    }
}
