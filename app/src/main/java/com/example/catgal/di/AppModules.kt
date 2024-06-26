package com.example.catgal.di

import com.example.catgal.data.remote.api.CatAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
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

    @IoDispatcher
    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IoDispatcher