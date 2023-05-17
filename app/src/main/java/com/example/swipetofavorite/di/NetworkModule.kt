package com.example.swipetofavorite.di

import com.example.swipetofavorite.retrofit.SwipeToFavApi
import com.example.swipetofavorite.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        val okHttpBuilder = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpBuilder.addInterceptor(httpLoggingInterceptor)



        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpBuilder.build())
            .build()
    }

    @Singleton
    @Provides
    fun providesSwipeToFavApi(retrofit: Retrofit): SwipeToFavApi {
        return retrofit.create(SwipeToFavApi::class.java)
    }

}