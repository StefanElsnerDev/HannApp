package com.example.hannapp.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.hannapp.data.repository.ProductSearchRepository
import javax.inject.Inject

class GetProductSearchResultsUseCase @Inject constructor(
    private val productSearchRepository: ProductSearchRepository
) {

    fun search(searchString: String, pageSize: Int) = Pager(
        PagingConfig(pageSize = pageSize)
    ) {
        productSearchRepository.search(
            searchString = searchString,
            pageSize = pageSize
        )
    }.flow
}
