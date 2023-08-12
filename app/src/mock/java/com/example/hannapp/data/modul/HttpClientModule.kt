package com.example.hannapp.data.modul

import com.example.hannapp.BuildConfig
import com.example.hannapp.data.remote.MockInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

    @Singleton
    @Provides
    @MockSearchInterceptor
    fun providesMockInterceptor(@MockSearchResponse mockResponse: String) = MockInterceptor(mockResponse)

    @Singleton
    @Provides
    fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, @MockSearchInterceptor mockInterceptor: MockInterceptor) =
        OkHttpClient
            .Builder()
            .addInterceptor(mockInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class MockSearchInterceptor
