package com.example.hannapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hannapp.data.modul.IoDispatcher
import com.example.hannapp.domain.GetNutritionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NutritionUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val nutritionNames: List<String> = emptyList()
)

@HiltViewModel
class NutritionViewModel @Inject constructor(
    private val getNutritionsUseCase: GetNutritionsUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(NutritionUiState(isLoading = true))
    val uiState: StateFlow<NutritionUiState> = _uiState.asStateFlow()

    init {
        getAll()
    }

    private fun getAll() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch(dispatcher) {
            getNutritionsUseCase()
                .catch { throwable ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            nutritionNames = emptyList(),
                            errorMessage = throwable.message ?: "Something went wrong"
                        )
                    }
                }
                .collect { names ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            nutritionNames = names ?: emptyList()
                        )
                    }
                }
        }
    }
}
