package com.example.catgal

import androidx.paging.PagingData
import com.example.catgal.domain.model.CatModel
import kotlinx.coroutines.flow.Flow

const val PAGE_SIZE = 10

sealed class Routes(val route: String) {
    object Gal: Routes("Gal")
    object Bookmarks: Routes("Bookmarks")
}

sealed interface UiState {
    object Loading: UiState
    data class Success(val data: Flow<PagingData<CatModel>>): UiState
}