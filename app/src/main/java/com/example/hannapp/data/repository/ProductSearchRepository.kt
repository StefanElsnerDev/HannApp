package com.example.hannapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.hannapp.data.remote.ProductSearchApi
import com.example.hannapp.data.source.ProductSearchPagingSource
import javax.inject.Inject

class ProductSearchRepository @Inject constructor(
    private val productSearchApi: ProductSearchApi
) {

    fun search(searchString: String, pageSize: Int) = Pager(
        PagingConfig(pageSize = pageSize)
    ) {
        ProductSearchPagingSource(
            productSearchApi = productSearchApi,
            searchString = searchString,
            pageSize = pageSize
        )
    }.flow
}
