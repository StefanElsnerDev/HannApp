package com.example.hannapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.hannapp.data.distinct.*
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.data.model.convert.NutritionConverter
import com.example.hannapp.data.modul.IoDispatcher
import com.example.hannapp.domain.DeleteNutritionUseCase
import com.example.hannapp.domain.GetNutritionUseCase
import com.example.hannapp.domain.UpdateNutritionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NutritionUpdateUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val nutritionUiModel: NutritionUiModel = NutritionUiModel(),
    val isValid: Boolean = false,
    val errors: Set<NutritionDataComponent> = emptySet(),
    val showErrors: Boolean = false
)

@HiltViewModel
class NutritionUpdateViewModel @Inject constructor(
    getNutritionUseCase: GetNutritionUseCase,
    private val updateNutritionUseCase: UpdateNutritionUseCase,
    private val deleteNutritionUseCase: DeleteNutritionUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    @Inject
    lateinit var nutritionConverter: NutritionConverter

    private val _uiState = MutableStateFlow(NutritionUpdateUiState(isLoading = true))
    val uiState: StateFlow<NutritionUpdateUiState> = _uiState.asStateFlow()

    private val _uiComponents = MutableStateFlow(
        listOf(
            Name(),
            Kcal(),
            Protein(),
            Fat(),
            Carbohydrates(),
            Sugar(),
            Fiber(),
            Alcohol()
        )
    )
    val uiComponents = _uiComponents.asStateFlow()

    val nutriments = getNutritionUseCase
        .getAll()
        .catch { throwable ->
            _uiState.update { state ->
                state.copy(
                    isLoading = false,
                    errorMessage = throwable.message ?: "Something went wrong"
                )
            }
        }
        .map { nutriments -> nutriments.map { nutritionConverter.entity(it).toUiModel() } }
        .cachedIn(viewModelScope)

    fun onNutritionChange(nutritionComponent: NutritionComponent, value: String) {
        _uiState.update { state ->
            state.copy(nutritionUiModel = nutritionComponent.update(state.nutritionUiModel, value))
        }
    }

    fun selectItem(nutritionUiModel: NutritionUiModel) {
        // TODO: Error Handling
        viewModelScope.launch(dispatcher) {
            _uiState.update { state ->
                state.copy(
                    nutritionUiModel = nutritionUiModel
                )
            }
        }
    }

    fun update() {
        viewModelScope.launch(dispatcher) {
            try {
                val isSuccess = NutritionConverter().uiModel(_uiState.value.nutritionUiModel).toEntity()
                    .let { updateNutritionUseCase(it) }

                if (!isSuccess) _uiState.update { it.copy(errorMessage = "Update failed") }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message) }
            }
        }
    }

    fun delete(nutritionUiModel: NutritionUiModel) {
        viewModelScope.launch(dispatcher) {
            deleteNutritionUseCase(nutritionConverter.uiModel(nutritionUiModel).toEntity())
        }
    }

    fun validate() {
        _uiComponents.value.forEach {
            _uiState.update { state ->
                state.copy(
                    errors = it.validate(state.nutritionUiModel, state.errors),
                    isValid = state.errors.isEmpty()
                )
            }
        }
        _uiState.update { state ->
            state.copy(isValid = state.errors.isEmpty())
        }
    }

    fun showErrors() = _uiState.update { state -> state.copy(showErrors = true) }

    fun resetError(nutritionDataComponent: NutritionDataComponent) {
        _uiState.update { state ->
            val errors = state.errors.toMutableSet()
            errors.remove(nutritionDataComponent)
            state.copy(errors = errors.toSet())
        }
    }
}
