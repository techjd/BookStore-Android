package com.techjd.bookstore.di

import com.google.gson.Gson
import com.techjd.bookstore.api.UserAPI
import com.techjd.bookstore.utils.Constants.DEVELOPMENT_BASE_URL
import com.techjd.bookstore.utils.DialogClass
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(DEVELOPMENT_BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun providesUserAPI(retrofit: Retrofit): UserAPI {
        return retrofit.create(UserAPI::class.java)
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return Gson()
    }
}