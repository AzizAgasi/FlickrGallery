package com.techdot.flickrgallery.di

import com.techdot.flickrgallery.Constants.Companion.BASE_URL
import com.techdot.flickrgallery.Constants.Companion.BASE_URL_QUERY
import com.techdot.flickrgallery.api.FlickrApi
import com.techdot.flickrgallery.api.FlickrApiQuery
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideFlickrApi(retrofit: Retrofit): FlickrApi =
        retrofit.create(FlickrApi::class.java)

    @Provides
    @Singleton
    fun provideFlickrApiQuery(retrofit: Retrofit): FlickrApiQuery =
        retrofit.create(FlickrApiQuery::class.java)
}