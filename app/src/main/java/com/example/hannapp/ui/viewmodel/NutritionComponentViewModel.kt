package com.example.hannapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.hannapp.data.distinct.*
import com.example.hannapp.data.model.NutritionUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ComponentUiState(
    val nutritionUiModel: NutritionUiModel = NutritionUiModel(),
    val errors: Set<NutritionDataComponent> = emptySet(),
    val isValid: Boolean = false,
    val showErrors: Boolean = false
)

abstract class NutritionComponentViewModel : ViewModel() {

    protected val _uiComponentState = MutableStateFlow(ComponentUiState())
    val uiComponentState = _uiComponentState.asStateFlow()

    protected val _uiComponents = MutableStateFlow(
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

    fun onNutritionChange(nutritionComponent: NutritionComponent, value: String) {
        _uiComponentState.update { state ->
            state.copy(nutritionUiModel = nutritionComponent.update(state.nutritionUiModel, value))
        }
    }

    fun validate() {
        _uiComponents.value.forEach {
            _uiComponentState.update { state ->
                state.copy(
                    errors = it.validate(state.nutritionUiModel, state.errors),
                    isValid = state.errors.isEmpty()
                )
            }
        }
        _uiComponentState.update { state ->
            state.copy(isValid = state.errors.isEmpty())
        }
    }

    fun showErrors() = _uiComponentState.update { state -> state.copy(showErrors = true) }

    fun resetError(nutritionDataComponent: NutritionDataComponent) {
        _uiComponentState.update { state ->
            val errors = state.errors.toMutableSet()
            errors.remove(nutritionDataComponent)
            state.copy(errors = errors.toSet())
        }
    }
}
