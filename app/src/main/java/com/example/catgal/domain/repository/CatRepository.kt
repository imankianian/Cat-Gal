package com.example.catgal.domain.repository

import androidx.paging.PagingData
import com.example.catgal.domain.model.CatModel
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    suspend fun getCat(): Flow<PagingData<CatModel>>
}