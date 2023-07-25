package com.example.hannapp.usecase

import androidx.paging.PagingData
import com.example.hannapp.data.repository.ProductSearchRepository
import com.example.hannapp.data.source.ProductSearchPagingSource
import com.example.hannapp.domain.GetProductSearchResultsUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetProductSearchResultsCaseShould {

    private lateinit var getProductSearchResultsUseCase: GetProductSearchResultsUseCase
    private val productSearchRepository: ProductSearchRepository = mock()
    private val productSearchPagingSource: ProductSearchPagingSource = mock()

    @BeforeEach
    fun beforeEach() {
        whenever(productSearchRepository.search(any(), any())).thenReturn(
            productSearchPagingSource
        )

        getProductSearchResultsUseCase = GetProductSearchResultsUseCase(
            productSearchRepository = productSearchRepository
        )
    }

    @Test
    fun invokeSearchOfRepository() = runTest {
        getProductSearchResultsUseCase.search("apple juice", 24).first()

        verify(productSearchRepository).search(any(), any())
    }

    @Test
    fun emitSearchResults() = runTest {
        val result = getProductSearchResultsUseCase.search("apple juice", 24).first()

        assertThat(result).isInstanceOf(PagingData::class.java)
    }
}
