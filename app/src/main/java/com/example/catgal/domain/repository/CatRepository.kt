package com.example.catgal.domain.repository

import com.example.catgal.domain.model.CatModel

interface CatRepository {
    suspend fun getCat(): List<CatModel>
}