package com.example.hannapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.hannapp.data.distinct.*
import com.example.hannapp.data.model.NutritionModel
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
    val nutritionModel: NutritionModel = NutritionModel(),
    val components: List<NutritionComponent> = listOf(
        Name(), Kcal(), Protein(), Fat(), Carbohydrates(), Sugar(), Fiber(), Alcohol(), Energy()
    ),
    val errors: Set<NutritionDataComponent> = emptySet()
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
        .map { nutriments -> nutriments.map { nutritionConverter.entity(it).toModel() } }
        .cachedIn(viewModelScope)

    fun onNutritionChange(nutritionComponent: NutritionComponent, value: String) {
        _uiState.update { state ->
            state.copy(nutritionModel = nutritionComponent.update(state.nutritionModel, value))
        }
    }

    fun selectItem(nutrition: NutritionModel) {
        // TODO: Error Handling
        viewModelScope.launch(dispatcher) {
            _uiState.update { state ->
                state.copy(
                    nutritionModel = nutrition
                )
            }
        }
    }

    fun update() {
        viewModelScope.launch(dispatcher) {
            try {
                val isSuccess = NutritionConverter().model(_uiState.value.nutritionModel).toEntity()
                    .let { updateNutritionUseCase(it) }

                if (!isSuccess) _uiState.update { it.copy(errorMessage = "Update failed") }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message) }
            }
        }
    }

    fun delete(nutrition: NutritionModel) {
        viewModelScope.launch(dispatcher) {
            deleteNutritionUseCase(nutritionConverter.model(nutrition).toEntity())
        }
    }
}
