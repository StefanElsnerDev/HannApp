package com.example.hannapp.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.hannapp.data.NetworkResult
import com.example.hannapp.data.model.api.Product
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class ProductSearchPagingSource @Inject constructor(
    private val productDataSource: ProductDataSource
) : PagingSource<Int, Product>() {

    var searchString: String = ""
    var pageSize: Int = 24

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val page = params.key ?: 1

        try {
            val result = productDataSource.search(
                searchString = searchString,
                page = page,
                pageSize = pageSize
            )

            return when (result) {
                is NetworkResult.Success -> {
                    LoadResult.Page(
                        data = result.data,
                        prevKey = if (page == 1) null else page,
                        nextKey = if (result.data.isEmpty()) null else page.plus(1)
                    )
                }

                is NetworkResult.Error -> LoadResult.Error(
                    throwable = HttpException(
                        Response.error<List<Product>>(
                            result.code,
                            result.message.toResponseBody()
                        )
                    )
                )

                is NetworkResult.Exception -> LoadResult.Error(throwable = result.e)
            }
        } catch (e: Exception) {
            return LoadResult.Error(throwable = e)
        }
    }
}
