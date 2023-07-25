package com.example.hannapp.repository

import com.example.hannapp.data.repository.ProductSearchRepository
import com.example.hannapp.data.source.ProductSearchPagingSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ProductSearchRepositoryShould {

    private lateinit var productSearchRepository: ProductSearchRepository
    private val productSearchPagingSource: ProductSearchPagingSource = mock()

    private val searchString = "Apple"
    private val pageSize = 24

    @BeforeEach
    fun beforeEach() {
        whenever(productSearchPagingSource.searchString).thenReturn(searchString)
        whenever(productSearchPagingSource.pageSize).thenReturn(pageSize)

        productSearchRepository = ProductSearchRepository(
            productSearchPagingSource = productSearchPagingSource
        )
    }

    @Test
    fun applySearchStringAndSizeToPagingSource() {
        productSearchRepository.search(
            searchString = searchString,
            pageSize = pageSize
        )

        assertThat(productSearchPagingSource.searchString).isEqualTo(searchString)
        assertThat(productSearchPagingSource.pageSize).isEqualTo(pageSize)
    }
}
