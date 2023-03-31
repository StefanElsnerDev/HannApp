package com.example.hannapp.data.modul

import com.example.hannapp.data.remote.ProductSearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideProductApi(retrofit: Retrofit): ProductSearchApi =
        retrofit.create(ProductSearchApi::class.java)
}
