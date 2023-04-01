package com.example.hannapp.domain

import com.example.hannapp.data.repository.ProductSearchRepository
import javax.inject.Inject

class GetProductSearchResultsUseCase @Inject constructor(
    private val productSearchRepository: ProductSearchRepository
) {
    fun search(searchString: String, pageSize: Int) =
        productSearchRepository.search(searchString, pageSize)
}
