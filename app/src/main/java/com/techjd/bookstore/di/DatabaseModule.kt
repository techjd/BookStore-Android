package com.techjd.bookstore.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.techjd.bookstore.db.AppDatabase
import com.techjd.bookstore.db.converter.Converter
import com.techjd.bookstore.db.dao.CartDao
import com.techjd.bookstore.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesAppDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        DATABASE_NAME
    )
        .build()

    @Singleton
    @Provides
    fun providesCartDao(db: AppDatabase): CartDao {
        return db.cartDao()
    }
}