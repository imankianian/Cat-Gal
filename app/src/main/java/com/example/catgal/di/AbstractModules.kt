package com.example.catgal.di

import com.example.catgal.data.repository.CatRepoImpl
import com.example.catgal.domain.repository.CatRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractModules {

    @Binds
    @Singleton
    abstract fun bindCatRepository(catRepoImpl: CatRepoImpl): CatRepository
}