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

    private var currentID = -1
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

    fun onNutritionTypeChange(nutritionComponent: NutritionComponent, value: String) {
        _uiState.update { state ->
            state.copy(nutritionComponentState = nutritionComponent.update(state.nutritionComponentState, value))
        }
        _uiState.update { state ->
            state.copy(nutrition = state.nutritionComponentState.mapToNutrition())
        }
    }

    fun selectItem(listIndex: Int) {
        viewModelScope.launch(dispatcher) {
            _uiState.update { state ->
                currentID = _uiState.value.foodList[listIndex].uid
                val nutrition = getNutritionUseCase(currentID)
                state.copy(
                    nutritionComponentState = nutrition?.mapToNutritionComponentState() ?: NutritionComponentState(),
                    nutrition = nutrition)
            }
        }
    }

    private fun NutritionComponentState.mapToNutrition() =
        Nutrition(
            uid = currentID,
            name = this.name.ifBlank { null },
            kcal = this.kcal.ifBlank { null },
            protein = this.protein.ifBlank { null },
            fad = this.fad.ifBlank { null },
            carbohydrates = this.carbohydrates.ifBlank { null },
            sugar = this.sugar.ifBlank { null },
            fiber = this.fiber.ifBlank { null },
            alcohol = this.alcohol.ifBlank { null },
            energyDensity = this.energy.ifBlank { null }
        )

    private fun Nutrition.mapToNutritionComponentState() =
        NutritionComponentState(
            name = this.name ?: "",
            kcal = this.kcal ?: "",
            protein = this.protein ?: "",
            fad = this.fad ?: "",
            carbohydrates = this.carbohydrates ?: "",
            sugar = this.sugar ?: "",
            fiber = this.fiber ?: "",
            alcohol = this.alcohol ?: "",
            energy = this.energyDensity ?: ""
        )
}
