package com.example.jetpackcompose.di.repository

import android.content.Context
import androidx.room.Room
import com.example.jetpackcompose.data.database.AppDatabase
import com.example.jetpackcompose.data.database.CartDao
import com.example.jetpackcompose.data.database.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "database-compose-sample",
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideProductDao(database: AppDatabase): ProductDao = database.productDao()

    @Singleton
    @Provides
    fun provideCartDao(database: AppDatabase): CartDao = database.cartDao()
}
