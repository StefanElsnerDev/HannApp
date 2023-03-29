package com.example.hannapp.data.model.api

data class ProductSearchResult(
    val count: Int,
    val page: Int,
    val pageCount: Int,
    val pageSize: Int,
    val skip: Int,
    val products: List<Product>
)
