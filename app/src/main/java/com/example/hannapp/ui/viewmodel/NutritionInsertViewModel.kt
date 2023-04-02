package com.example.hannapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.hannapp.data.distinct.*
import com.example.hannapp.data.model.NutritionModel
import com.example.hannapp.data.model.api.Product
import com.example.hannapp.data.model.entity.Nutrition
import com.example.hannapp.data.modul.IoDispatcher
import com.example.hannapp.domain.GetProductSearchResultsUseCase
import com.example.hannapp.domain.InsertNutritionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NutritionInsertState(
    val nutrition: NutritionModel = NutritionModel(),
    val errors: Set<NutritionDataComponent> = emptySet(),
    val isValid: Boolean = false,
    val showErrors: Boolean = false
)

@HiltViewModel
class NutritionInsertViewModel @Inject constructor(
    private val insertNutritionUseCase: InsertNutritionUseCase,
    private val getProductSearchResultsUseCase: GetProductSearchResultsUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(NutritionInsertState())
    val uiState = _uiState.asStateFlow()

    private val _uiComponents = MutableStateFlow(
        listOf(
            Name(),
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

    private var _products = MutableSharedFlow<PagingData<Product>>()
    val products: Flow<PagingData<Product>> =
        _products.cachedIn(viewModelScope)//.search("", 24).cachedIn(viewModelScope)

    fun search(searchString: String) {
        viewModelScope.launch(dispatcher) {
            getProductSearchResultsUseCase.search(searchString, 24).collectLatest {
                _products.emit(it)
            }
        }
    }

    fun insert() {
        viewModelScope.launch(dispatcher) {
            // TODO error handling on failing insertion
            insertNutritionUseCase(
                Nutrition(
                    name = _uiState.value.nutrition.name,
                    kcal = _uiState.value.nutrition.kcal,
                    protein = _uiState.value.nutrition.protein,
                    fad = _uiState.value.nutrition.fad,
                    carbohydrates = _uiState.value.nutrition.carbohydrates,
                    sugar = _uiState.value.nutrition.sugar,
                    fiber = _uiState.value.nutrition.fiber,
                    alcohol = _uiState.value.nutrition.alcohol,
                    energyDensity = _uiState.value.nutrition.energy
                )
            )
            clearState()
        }
    }

    fun onNutritionChange(nutritionComponent: NutritionComponent, value: String) {
        _uiState.update { state ->
            state.copy(nutrition = nutritionComponent.update(state.nutrition, value))
        }
    }

    fun validate() {
        _uiComponents.value.forEach {
            _uiState.update { state ->
                state.copy(
                    errors = it.validate(state.nutrition, state.errors),
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

    fun clearState() {
        _uiState.update { NutritionInsertState() }
    }

    fun select(product: Product) {
        _uiState.update { state ->
            state.copy(
                nutrition = NutritionModel(
                    name = product.productName,
                    kcal = product.nutriments.kcal.toString(),
                    protein = product.nutriments.protein.toString(),
                    fad = product.nutriments.fat.toString(),
                    carbohydrates = product.nutriments.carbohydrates.toString(),
                    sugar = product.nutriments.sugar.toString(),
                    fiber = product.nutriments.fiber.toString(),
                    alcohol = product.nutriments.alcohol.toString(),
                    energy = product.nutriments.kcal.toString()
                )
            )
        }
        validate()
    }
}
