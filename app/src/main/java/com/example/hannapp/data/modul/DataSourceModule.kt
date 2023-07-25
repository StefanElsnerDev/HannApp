package com.example.hannapp.data.modul

import com.example.hannapp.data.source.ProductDataSource
import com.example.hannapp.data.source.ProductDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindProductSearch(
        productDataSourceImpl: ProductDataSourceImpl
    ): ProductDataSource
}
