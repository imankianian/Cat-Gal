package com.example.catgal.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.catgal.PAGE_SIZE
import com.example.catgal.data.CatPagingSource
import com.example.catgal.di.IoDispatcher
import com.example.catgal.domain.model.CatModel
import com.example.catgal.domain.repository.CatRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CatRepoImpl @Inject constructor(
    private val catPagingSource: CatPagingSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): CatRepository {

    override suspend fun getCat(): Flow<PagingData<CatModel>> = withContext(dispatcher) {
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = { catPagingSource }
        ).flow
    }
}