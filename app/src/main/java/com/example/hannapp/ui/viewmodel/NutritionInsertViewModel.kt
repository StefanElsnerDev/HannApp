package com.example.hannapp.ui.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.data.model.api.Product
import com.example.hannapp.data.model.convert.NutritionConverter
import com.example.hannapp.data.modul.IoDispatcher
import com.example.hannapp.domain.GetProductSearchResultsUseCase
import com.example.hannapp.domain.InsertNutritionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutritionInsertViewModel @Inject constructor(
    private val insertNutritionUseCase: InsertNutritionUseCase,
    private val getProductSearchResultsUseCase: GetProductSearchResultsUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : NutritionComponentViewModel() {


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
            insertNutritionUseCase( NutritionConverter().uiModel(_uiComponentState.value.nutritionUiModel).toEntity())
            clearState()
        }
    }

    fun clearState() {
        _uiComponentState.update { ComponentUiState() }
    }

    fun select(product: Product) {
        _uiComponentState.update { state ->
            state.copy(
                nutritionUiModel = NutritionUiModel(
                    name = product.productName,
                    kcal = product.nutriments.kcal.toString(),
                    protein = product.nutriments.protein.toString(),
                    fat = product.nutriments.fat.toString(),
                    carbohydrates = product.nutriments.carbohydrates.toString(),
                    sugar = product.nutriments.sugar.toString(),
                    fiber = product.nutriments.fiber.toString(),
                    alcohol = product.nutriments.alcohol.toString(),
                )
            )
        }
        validate()
    }
}
