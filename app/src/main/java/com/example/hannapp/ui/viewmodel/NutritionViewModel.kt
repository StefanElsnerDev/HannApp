package com.example.hannapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hannapp.data.modul.IoDispatcher
import com.example.hannapp.domain.GetNutritionBMIsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NutritionUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val nutritionNames: List<String> = emptyList()
)

@HiltViewModel
class NutritionViewModel @Inject constructor(
    private val getNutritionBMIsUseCase: GetNutritionBMIsUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(NutritionUiState(isLoading = true))
    val uiState: StateFlow<NutritionUiState> = _uiState.asStateFlow()

    init {
        getAll()
    }

    fun insertDummy() =
        viewModelScope.launch(dispatcher) {
            getNutritionBMIsUseCase.insertDummy()
        }

    private fun getAll() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch(dispatcher) {
            getNutritionBMIsUseCase().collect { names ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        nutritionNames = names
                    )
                }
            }
        }
    }
}
