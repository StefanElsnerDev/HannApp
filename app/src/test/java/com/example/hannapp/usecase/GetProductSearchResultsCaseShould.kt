package com.example.hannapp.usecase

import androidx.paging.PagingData
import com.example.hannapp.data.model.api.Nutriments
import com.example.hannapp.data.model.api.Product
import com.example.hannapp.data.repository.ProductSearchRepository
import com.example.hannapp.domain.GetProductSearchResultsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class GetProductSearchResultsCaseShould {

    private lateinit var getProductSearchResultsUseCase: GetProductSearchResultsUseCase
    private val productSearchRepository: ProductSearchRepository = mock()

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

    private val pagingData = PagingData.from(products)

    @BeforeEach
    fun beforeEach() {
        whenever(productSearchRepository.search(any(), any())).thenReturn(
            flowOf(pagingData)
        )

        getProductSearchResultsUseCase = GetProductSearchResultsUseCase(
            productSearchRepository = productSearchRepository
        )
    }

    @Test
    fun invokeSearchOfRepository() {
        getProductSearchResultsUseCase.search("apple juice", 24)

        verify(productSearchRepository).search(any(), any())
    }

    @Test
    fun emitSearchResults() = runTest {
        val result = getProductSearchResultsUseCase.search("apple juice", 24)

        Assertions.assertEquals(
            pagingData,
            result.first()
        )
    }
}
