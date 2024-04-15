package com.example.catgal.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.catgal.PAGE_SIZE
import com.example.catgal.data.CatPagingSource
import com.example.catgal.domain.model.CatModel
import com.example.catgal.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatRepoImpl @Inject constructor(
    private val catPagingSource: CatPagingSource
): CatRepository {

    override suspend fun getCat(): Flow<PagingData<CatModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { catPagingSource }
        ).flow
    }
}