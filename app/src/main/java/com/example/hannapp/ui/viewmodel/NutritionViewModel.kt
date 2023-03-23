package com.example.hannapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hannapp.data.model.Food
import com.example.hannapp.data.modul.IoDispatcher
import com.example.hannapp.domain.GetFoodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NutritionUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val foodList: List<Food> = emptyList()  //TODO ViewModel State
)

@HiltViewModel
class NutritionViewModel @Inject constructor(
    private val getFoodUseCase: GetFoodUseCase,
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
            getFoodUseCase()
                .catch { throwable ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            foodList = emptyList(),
                            errorMessage = throwable.message ?: "Something went wrong"
                        )
                    }
                }
                .collect { names ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            foodList = names ?: emptyList()
                        )
                    }
                }
        }
    }
}
