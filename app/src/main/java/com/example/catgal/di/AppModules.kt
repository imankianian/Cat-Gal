package com.example.catgal.di

import com.example.catgal.data.remote.api.CatAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    private val BASE_URL = "https://api.thecatapi.com/"

    @Provides
    @Singleton
    fun provideCatAPI(): CatAPI = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()
        .create(CatAPI::class.java)

}