package com.example.catgal.data.remote.api

import com.example.catgal.data.remote.dto.RemoteCatModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CatAPI {

    @Headers("x-api-key:WELDY")
    @GET("v1/images/search")
    suspend fun fetchCats(
        @Query("size") size: String = "med",
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int
    ): Response<List<RemoteCatModel>>
}