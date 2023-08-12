package com.example.hannapp.data.modul

import android.content.Context
import com.example.hannapp.utils.JsonReader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MockModule {

    @Singleton
    @Provides
    @MockSearchResponse
    fun provideMockSearchResponse(@ApplicationContext context: Context) = JsonReader(context).jsonToString("MockSearchResponse.json")
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class MockSearchResponse
