package com.example.catgal.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catgal.UiState
import com.example.catgal.domain.usecase.GetCatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCatUseCase: GetCatUseCase
): ViewModel() {

    private var _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        getCat()
    }

    private fun getCat() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = getCatUseCase.getCat()
            _uiState.value = UiState.Success(result)
        }
    }
}