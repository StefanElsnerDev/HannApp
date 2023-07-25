package com.example.hannapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.hannapp.data.source.ProductDataSource
import com.example.hannapp.data.source.ProductSearchPagingSource
import javax.inject.Inject

class ProductSearchRepository @Inject constructor(
    private val productDataSource: ProductDataSource
) {

    fun search(searchString: String, pageSize: Int) = Pager(
        PagingConfig(pageSize = pageSize)
    ) {
        ProductSearchPagingSource(
            productDataSource = productDataSource,
            searchString = searchString,
            pageSize = pageSize
        )
    }.flow
}
