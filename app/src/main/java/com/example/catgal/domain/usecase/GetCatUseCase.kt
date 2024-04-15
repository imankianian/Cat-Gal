package com.example.catgal.domain.usecase

import com.example.catgal.domain.model.CatModel
import com.example.catgal.domain.repository.CatRepository
import javax.inject.Inject

class GetCatUseCase @Inject constructor(
    private val catRepository: CatRepository
) {

    suspend fun getCat(): List<CatModel> {
        return catRepository.getCat()
    }
}