package com.example.hannapp.repository

import com.example.hannapp.data.remote.ProductSearchApi
import com.example.hannapp.data.repository.ProductSearchRepository
import org.junit.jupiter.api.BeforeEach
import org.mockito.kotlin.mock

class ProductSearchRepositoryShould {

    private lateinit var productSearchRepository: ProductSearchRepository
    private val productSearchApi: ProductSearchApi = mock()

    @BeforeEach
    fun beforeEach() {
        productSearchRepository = ProductSearchRepository(
            productSearchApi = productSearchApi
        )
    }

    /**
     * TODO Add test cases
     * Suggested implementation architecture
     * @see [Android Developer Docs](https://developer.android.com/topic/libraries/architecture/paging/v3-paged-data#pagingdata-stream)
     * complicates tests due to encapsulated Paging- and PagingSourceFactory-object
     */
}
