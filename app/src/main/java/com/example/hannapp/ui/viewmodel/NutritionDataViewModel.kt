package com.example.hannapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hannapp.data.model.entity.Nutrition
import com.example.hannapp.data.modul.IoDispatcher
import com.example.hannapp.data.distinct.NutritionComponent
import com.example.hannapp.domain.InsertNutritionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
    val energy: String = ""
)

@HiltViewModel
class NutritionDataViewModel @Inject constructor(
    private val insertNutritionUseCase: InsertNutritionUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(NutritionComponentState())
    val uiState = _uiState.asStateFlow()

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

    fun onNutritionTypeChange(type: NutritionComponent, value: String) {
        _uiState.update { state ->
            when (type) {
                NutritionComponent.NAME -> state.copy(name = value)
                NutritionComponent.KCAL -> state.copy(kcal = value)
                NutritionComponent.PROTEIN -> state.copy(protein = value)
                NutritionComponent.FAD -> state.copy(fad = value)
                NutritionComponent.CARBOHYDRATES -> state.copy(carbohydrates = value)
                NutritionComponent.SUGAR -> state.copy(sugar = value)
                NutritionComponent.FIBER -> state.copy(fiber = value)
                NutritionComponent.ALCOHOL -> state.copy(alcohol = value)
                NutritionComponent.ENERGY -> state.copy(energy = value)
            }
        }
    }
}
