package com.example.catgal.domain.usecase

import androidx.paging.PagingData
import com.example.catgal.domain.model.CatModel
import com.example.catgal.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCatUseCase @Inject constructor(
    private val catRepository: CatRepository
) {

    suspend fun getCat(): Flow<PagingData<CatModel>> {
        return catRepository.getCat()
    }
}