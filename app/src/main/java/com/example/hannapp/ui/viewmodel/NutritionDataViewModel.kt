package com.example.hannapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hannapp.data.distinct.*
import com.example.hannapp.data.model.entity.Nutrition
import com.example.hannapp.data.modul.IoDispatcher
import com.example.hannapp.domain.InsertNutritionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NutritionComponentState(
    val name: String = "",
    val kcal: String = "",
    val protein: String = "",
    val fad: String = "",
    val carbohydrates: String = "",
    val sugar: String = "",
    val fiber: String = "",
    val alcohol: String = "",
    val energy: String = "",
)

@HiltViewModel
class NutritionDataViewModel @Inject constructor(
    private val insertNutritionUseCase: InsertNutritionUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(NutritionComponentState())
    val uiState = _uiState.asStateFlow()

    private val _uiComponents = MutableStateFlow(
        listOf(
            Kcal(),
            Protein(),
            Fad(),
            Carbohydrates(),
            Sugar(),
            Fiber(),
            Alcohol(),
            Energy()
        )
    )
    val uiComponents = _uiComponents.asStateFlow()

    fun insert() {
        viewModelScope.launch(dispatcher) {
            insertNutritionUseCase(
                Nutrition(
                    name = _uiState.value.name,
                    kcal = _uiState.value.kcal,
                    protein = _uiState.value.protein,
                    fad = _uiState.value.fad,
                    carbohydrates = _uiState.value.carbohydrates,
                    sugar = _uiState.value.sugar,
                    fiber = _uiState.value.fiber,
                    alcohol = _uiState.value.alcohol,
                    energyDensity = _uiState.value.energy
                )
            )
        }
    }

    fun onNutritionTypeChange(nutritionComponent: NutritionComponent, value: String) {
        _uiState.update { state ->
            nutritionComponent.update(state, value)
        }
    }
}
