package com.example.hannapp.data.modul

import android.content.Context
import androidx.room.Room
import com.example.hannapp.Constants.DATABASE_NAME
import com.example.hannapp.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DATABASE_NAME
    )
        .build()

    @Provides
    @Singleton
    fun provideNutritionDao(db: AppDatabase) = db.nutritionDao()

    @Provides
    @Singleton
    fun provideNutritionLogDao(db: AppDatabase) = db.nutrimentLogDao()
}
