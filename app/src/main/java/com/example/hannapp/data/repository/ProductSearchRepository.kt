package com.example.hannapp.data.repository

import com.example.hannapp.data.source.ProductSearchPagingSource
import javax.inject.Inject

class ProductSearchRepository @Inject constructor(
    private val productSearchPagingSource: ProductSearchPagingSource
) {

    fun search(searchString: String, pageSize: Int) = productSearchPagingSource.apply {
        this.searchString = searchString
        this.pageSize = pageSize
    }
}
