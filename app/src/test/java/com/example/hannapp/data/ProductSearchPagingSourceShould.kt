package com.example.hannapp.data

import androidx.paging.PagingSource
import com.example.hannapp.data.model.api.Nutriments
import com.example.hannapp.data.model.api.Product
import com.example.hannapp.data.source.ProductDataSource
import com.example.hannapp.data.source.ProductSearchPagingSource
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class ProductSearchPagingSourceShould {

    private lateinit var productSearchPagingSource: ProductSearchPagingSource
    private val productDataSource: ProductDataSource = mock()
    private val loadParams: PagingSource.LoadParams<Int> = mock()

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

    private val result = NetworkResult.Success(products)

    @BeforeEach
    fun beforeEach() = runTest {
        whenever(productDataSource.search(any(), eq(firstPage), any())).thenReturn(result)

        productSearchPagingSource = ProductSearchPagingSource(
            productDataSource = productDataSource
        )
    }

    @Test
    fun callProductSearchApi() = runTest {
        productSearchPagingSource.load(loadParams)

        verify(productDataSource).search(any(), any(), any())
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
        val error = NetworkResult.Error<List<Product>>(code = 401, message = "Authentication failed")

        whenever(productDataSource.search(any(), eq(firstPage), any())).thenReturn(
            error
        )

        val result = productSearchPagingSource.load(loadParams)

        assert(result is PagingSource.LoadResult.Error<Int, Product>)
    }

    @Test
    fun returnErrorLoadResultOnException() = runTest {
        val exception = RuntimeException("Something terrible happened")

        whenever(productDataSource.search(any(), eq(firstPage), any())).thenThrow(
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
