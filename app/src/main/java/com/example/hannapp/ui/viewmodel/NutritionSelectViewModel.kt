package com.example.hannapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.hannapp.data.model.NutrimentLogModel
import com.example.hannapp.data.model.NutrimentUiLogModel
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.data.model.convert.NutritionConverter
import com.example.hannapp.data.model.entity.Nutrition
import com.example.hannapp.data.modul.IoDispatcher
import com.example.hannapp.domain.GetNutrimentLogUseCase
import com.example.hannapp.domain.GetNutritionUseCase
import com.example.hannapp.domain.InsertNutrimentLogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NutritionUiState(
    val nutrimentLog: List<NutrimentUiLogModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
@HiltViewModel
class NutritionSelectViewModel @Inject constructor(
    private val getNutritionUseCase: GetNutritionUseCase,
    private val insertNutrimentLogUseCase: InsertNutrimentLogUseCase,
    getNutrimentLogUseCase: GetNutrimentLogUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(NutritionUiState(isLoading = true))
    val uiState: StateFlow<NutritionUiState> = _uiState.asStateFlow()

    private var _nutriments = MutableSharedFlow<PagingData<Nutrition>>()
    val nutriments = _nutriments.cachedIn(viewModelScope)

    fun getAll() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch(dispatcher) {
            getNutritionUseCase.getAll()
                .catch { throwable ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            errorMessage = throwable.message ?: "Something went wrong"
                        )
                    }
                }
                .collectLatest { nutriments ->
                    _nutriments.emit(nutriments)
                }
        }
    }

    fun add(nutritionUiModel: NutritionUiModel, quantity: Double) {
        viewModelScope.launch(dispatcher) {
            try {
                val isSuccess = insertNutrimentLogUseCase(
                    nutrimentLogModel = NutrimentLogModel(
                        nutrition = NutritionConverter().uiModel(nutritionUiModel).toEntity(),
                        quantity = quantity,
                        createdAt = System.currentTimeMillis(),
                        modifiedAt = null
                    )
                )
                if (!isSuccess){
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            errorMessage = "Nutriment could not be logged"
                        )
                    }
                }
            } catch (e: Exception){
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
            }
        }
    }
}
