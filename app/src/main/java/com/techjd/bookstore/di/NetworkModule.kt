package com.techjd.bookstore.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.techjd.bookstore.R
import com.techjd.bookstore.api.BuyerAPI
import com.techjd.bookstore.api.UserAPI
import com.techjd.bookstore.models.failure.Failure
import com.techjd.bookstore.utils.Constants.DEVELOPMENT_BASE_URL
import com.techjd.bookstore.utils.DialogClass
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.ResponseBody
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
    fun providesBuyerAPI(retrofit: Retrofit): BuyerAPI {
        return retrofit.create(BuyerAPI::class.java)
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ): RequestManager {
        return Glide.with(context).setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.bookshop)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
        )
    }
}