package com.example.hannapp.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.hannapp.data.model.api.Product
import com.example.hannapp.data.remote.ProductSearchApi
import retrofit2.HttpException

class ProductSearchPagingSource(
    private val productSearchApi: ProductSearchApi,
    private val searchString: String,
    private val pageSize: Int
) : PagingSource<Int, Product>() {

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val page = params.key ?: 1
        return try {
            val response = productSearchApi.search(
                searchString = searchString,
                page = page,
                pageSize = pageSize
            )

            if (response.isSuccessful) {
                val resultBody = response.body()

                require(resultBody!= null)

                LoadResult.Page(
                    data = resultBody.products,
                    prevKey = if (page == 1) null else page,
                    nextKey = if (resultBody.pageCount <= resultBody.pageSize) null else page.plus(1)
                )
            } else {
                return LoadResult.Error(
                    HttpException(response)
                )
            }

        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}
