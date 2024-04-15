package com.example.catgal.data.remote.api

import com.example.catgal.data.remote.dto.RemoteCatModel
import retrofit2.http.GET

interface CatAPI {

    @GET("v1/images/search?size=med&limit=10&page=2")
    suspend fun fetchCats(): List<RemoteCatModel>
}