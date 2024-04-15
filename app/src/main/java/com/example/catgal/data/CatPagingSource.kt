package com.example.catgal.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.catgal.PAGE_SIZE
import com.example.catgal.data.remote.api.CatAPI
import com.example.catgal.data.remote.dto.RemoteCatModel
import com.example.catgal.di.IoDispatcher
import com.example.catgal.domain.model.CatModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CatPagingSource @Inject constructor(
    private val catAPI: CatAPI,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): PagingSource<Int, CatModel>() {

    private val STARTING_PAGE_INDEX = 1

    override suspend fun load(params: LoadParams<Int>) = withContext(dispatcher) {
        val position = params.key ?: STARTING_PAGE_INDEX

        // Adding this delay intentionally to simulate network delay and show progress indicator
        delay(2000)
        val response = catAPI.fetchCats(page = position)
        if (response.isSuccessful && response.body() != null) {
            val catModels = convertRemoteCatModelToDomainCatModel(response.body()!!)
            val nextKey = if (catModels.isEmpty()) {
                null
            } else {
                position + (params.loadSize / PAGE_SIZE)
            }
            LoadResult.Page(
                data = catModels,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } else {
            LoadResult.Error(Throwable("${response.message()}: ${response.code()}"))
        }
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

    override fun getRefreshKey(state: PagingState<Int, CatModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}