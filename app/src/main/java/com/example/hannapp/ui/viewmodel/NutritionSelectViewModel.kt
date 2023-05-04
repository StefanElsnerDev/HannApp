package com.example.hannapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.hannapp.data.model.NutrimentLogModel
import com.example.hannapp.data.model.NutrimentUiLogModel
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.data.model.convert.NutritionConverter
import com.example.hannapp.data.model.entity.Nutrition
import com.example.hannapp.data.modul.IoDispatcher
import com.example.hannapp.domain.DeleteNutrimentLogUseCase
import com.example.hannapp.domain.GetNutrimentLogUseCase
import com.example.hannapp.domain.GetNutritionUseCase
import com.example.hannapp.domain.InsertNutrimentLogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NutritionUiState(
    var cachedNutritionUiModel: NutritionUiModel = NutritionUiModel(),
    var isSelectionValid: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class NutritionSelectViewModel @Inject constructor(
    private val getNutritionUseCase: GetNutritionUseCase,
    private val insertNutrimentLogUseCase: InsertNutrimentLogUseCase,
    private val deleteNutrimentLogUseCase: DeleteNutrimentLogUseCase,
    getNutrimentLogUseCase: GetNutrimentLogUseCase,
    private val nutritionConverter: NutritionConverter,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(NutritionUiState(isLoading = true))
    val uiState: StateFlow<NutritionUiState> = _uiState.asStateFlow()

    private var _nutriments = MutableSharedFlow<PagingData<Nutrition>>()
    val nutriments = _nutriments
        .map { nutriments -> nutriments.map { nutritionConverter.entity(it).toUiModel() } }
        .cachedIn(viewModelScope)

    val nutrimentLog: StateFlow<List<NutrimentUiLogModel>> = getNutrimentLogUseCase.observeNutrimentLog()
        .catch {
            _uiState.update { state ->
                state.copy(
                    isLoading = false,
                    errorMessage = it.message
                )
            }
        }.map { list ->
            list.map {
                NutrimentUiLogModel(
                    nutrition = nutritionConverter.entity(it.nutrition).toUiModel(),
                    quantity = it.quantity,
                    unit = "",
                    timeStamp = it.createdAt
                )
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        )

    fun getAll() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch(dispatcher) {
            getNutritionUseCase.getAll()
                .catch { throwable ->
                    updateErrorState(throwable.message ?: "Loading of nutriments from database failed")
                }
                .collectLatest { nutriments ->
                    _nutriments.emit(nutriments)
                }
        }
    }

    fun select(nutritionUiModel: NutritionUiModel) {
        nutritionUiModel.apply {
            when (id) {
                null -> updateErrorState("Invalid selection")
                else -> _uiState.update { state ->
                    state.copy(
                        cachedNutritionUiModel = this
                    )
                }
            }
        }
        validateSelection()
    }

    fun add(quantity: Double) {
        viewModelScope.launch(dispatcher) {
            try {
                val isSuccess = insertNutrimentLogUseCase(
                    nutrimentLogModel = NutrimentLogModel(
                        nutrition = nutritionConverter.uiModel(_uiState.value.cachedNutritionUiModel).toEntity(),
                        quantity = quantity,
                        createdAt = System.currentTimeMillis(),
                        modifiedAt = null
                    )
                )
                if (!isSuccess){
                    updateErrorState("Nutriment could not be logged")
                }
            } catch (e: Exception){
                updateErrorState(e.message ?: "Addition of log entry failed")
            }
        }
    }

    fun castAsDouble(value: String, function: (Double)->Unit) {
        val nullableValue = value.toDoubleOrNull()
        if(nullableValue != null){
            nullableValue.apply(function)
        } else {
            _uiState.update { state ->
                state.copy(
                    isLoading = false,
                    errorMessage = "Invalid Input"
                )
            }
        }
    }

    private fun updateErrorState(message: String) {
        _uiState.update { state ->
            state.copy(
                isLoading = false,
                errorMessage = message
            )
        }
    }

    fun clearAll() {
        viewModelScope.launch(dispatcher) {
            try {
                val isCleared = deleteNutrimentLogUseCase.clear()

                if (!isCleared) updateErrorState("Deletion failed")
            } catch (e: Exception) {
                updateErrorState(e.message ?: "Unexpected error on deletion")
            }
        }

        unselect()
        validateSelection()
    }

    private fun unselect(){
        _uiState.value.cachedNutritionUiModel = NutritionUiModel()
    }

    private fun validateSelection(){
        _uiState.update { state ->
            state.copy(
                isSelectionValid = state.cachedNutritionUiModel.id != null
            )
        }
    }
}
