package com.example.hannapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hannapp.data.distinct.*
import com.example.hannapp.data.model.Food
import com.example.hannapp.data.model.NutritionModel
import com.example.hannapp.data.model.convert.NutritionConverter
import com.example.hannapp.data.modul.IoDispatcher
import com.example.hannapp.domain.GetFoodUseCase
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
    val foodList: List<Food> = emptyList(),
    val components: List<NutritionComponent> = listOf(
        Name(), Kcal(), Protein(), Fad(), Carbohydrates(), Sugar(), Fiber(), Alcohol(), Energy()
    ),
    val errors: Set<NutritionDataComponent> = emptySet()
    )

@HiltViewModel
class NutritionUpdateViewModel @Inject constructor(
    private val getFoodUseCase: GetFoodUseCase,
    private val getNutritionUseCase: GetNutritionUseCase,
    private val updateNutritionUseCase: UpdateNutritionUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(NutritionUpdateUiState(isLoading = true))
    val uiState: StateFlow<NutritionUpdateUiState> = _uiState.asStateFlow()

    var currentListIndex = 0

    init {
        viewModelScope.launch(dispatcher) {
            catchFood().collect { names ->
                names.copy()
                selectItem(currentListIndex)
            }
        }
    }

    private fun List<Food>?.copy() {
        _uiState.update { state ->
            state.copy(
                isLoading = false, foodList = this ?: emptyList()
            )
        }
    }

    private fun catchFood() = getFoodUseCase()
        .catch { throwable ->
            _uiState.update { state ->
                state.copy(
                    isLoading = false,
                    foodList = emptyList(),
                    errorMessage = throwable.message ?: "Something went wrong"
                )
            }
        }

    fun onNutritionChange(nutritionComponent: NutritionComponent, value: String) {
        _uiState.update { state ->
            state.copy(nutritionModel = nutritionComponent.update(state.nutritionModel, value))
        }
    }

    fun selectItem(listIndex: Int) {
        // TODO: Error Handling
        viewModelScope.launch(dispatcher) {
            val nutrition = getNutritionUseCase(_uiState.value.foodList[listIndex].uid)

            nutrition?.let {
                _uiState.update { state ->
                    state.copy(
                        nutritionModel = NutritionConverter().entity(nutrition).toModel()
                    )
                }
            }
        }
    }

    fun update(){
        viewModelScope.launch(dispatcher) {
            try {
                val isSuccess = NutritionConverter().model(_uiState.value.nutritionModel).toEntity()
                    .let { updateNutritionUseCase(it) }

                if (!isSuccess) _uiState.update { it.copy(errorMessage = "Update failed") }
            } catch (e: Exception){
                _uiState.update { it.copy(errorMessage = e.message) }
            }
        }
    }
}
