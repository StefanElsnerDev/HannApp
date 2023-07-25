package com.example.hannapp.repository

import com.example.hannapp.data.repository.ProductSearchRepository
import com.example.hannapp.data.source.ProductDataSource
import org.junit.jupiter.api.BeforeEach
import org.mockito.kotlin.mock

class ProductSearchRepositoryShould {

    private lateinit var productSearchRepository: ProductSearchRepository
    private val productDataSource: ProductDataSource = mock()

    @BeforeEach
    fun beforeEach() {
        productSearchRepository = ProductSearchRepository(
            productDataSource = productDataSource
        )
    }

    /**
     * TODO Add test cases
     * Suggested implementation architecture
     * @see [Android Developer Docs](https://developer.android.com/topic/libraries/architecture/paging/v3-paged-data#pagingdata-stream)
     * complicates tests due to encapsulated Paging- and PagingSourceFactory-object
     */
}
