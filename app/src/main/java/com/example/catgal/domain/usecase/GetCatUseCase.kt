package com.example.catgal.domain.usecase

import androidx.paging.PagingData
import com.example.catgal.di.IoDispatcher
import com.example.catgal.domain.model.CatModel
import com.example.catgal.domain.repository.CatRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCatUseCase @Inject constructor(
    private val catRepository: CatRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun getCat(): Flow<PagingData<CatModel>> {
        return withContext(dispatcher) {
            catRepository.getCat()
        }
    }
}