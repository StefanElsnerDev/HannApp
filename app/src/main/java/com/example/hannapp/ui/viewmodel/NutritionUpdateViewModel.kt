package com.example.hannapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hannapp.data.distinct.*
import com.example.hannapp.data.model.Food
import com.example.hannapp.data.model.entity.Nutrition
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
    val nutritionComponentState: NutritionComponentState = NutritionComponentState(),
    val nutrition: Nutrition? = null,
    val foodList: List<Food> = emptyList(),
    val components: List<NutritionComponent> = listOf(
        Name(), Kcal(), Protein(), Fad(), Carbohydrates(), Sugar(), Fiber(), Alcohol(), Energy()
    )
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

    init {
        viewModelScope.launch(dispatcher) {
            catchFood().collect { names ->
                names.copy()
                selectItem(0)
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

    fun onNutritionTypeChange(nutritionComponent: NutritionComponent, value: String) {
        _uiState.update { state ->
            state.copy(nutritionComponentState = nutritionComponent.update(state.nutritionComponentState, value))
        }
    }

    fun selectItem(listIndex: Int) {
        viewModelScope.launch(dispatcher) {
            _uiState.update { state ->
                val id = _uiState.value.foodList[listIndex].uid
                state.copy(nutrition = getNutritionUseCase(id))
            }
        }
    }
}
