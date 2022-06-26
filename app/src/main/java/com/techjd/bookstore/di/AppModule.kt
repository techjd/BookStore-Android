package com.techjd.bookstore.di

import com.techjd.bookstore.ui.fragment.ModalBottomSheetSort
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun providesBottomSheet(): ModalBottomSheetSort {
        return ModalBottomSheetSort()
    }
}