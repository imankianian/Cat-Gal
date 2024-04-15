package com.example.catgal.data.repository

import com.example.catgal.data.remote.api.CatAPI
import com.example.catgal.data.remote.dto.RemoteCatModel
import com.example.catgal.domain.model.CatModel
import com.example.catgal.domain.repository.CatRepository
import javax.inject.Inject

class CatRepoImpl @Inject constructor(
    private val catAPI: CatAPI
): CatRepository {

    override suspend fun getCat(): List<CatModel> {
        val result = catAPI.fetchCats()
        return convertRemoteCatModelToDomainCatModel(result)
    }


    private fun convertRemoteCatModelToDomainCatModel(remoteCatModels: List<RemoteCatModel>): List<CatModel> {
        val catModels = mutableListOf<CatModel>()
        remoteCatModels.forEach { remoteCatModel ->
            val catModel = CatModel(
                remoteCatModel.id,
                remoteCatModel.url,
                remoteCatModel.width,
                remoteCatModel.height
            )
            catModels.add(catModel)
        }
        return catModels
    }
}