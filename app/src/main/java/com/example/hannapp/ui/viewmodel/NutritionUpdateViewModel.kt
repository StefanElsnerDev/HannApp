package com.example.hannapp.ui.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
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
    val nutritionUiModel: NutritionUiModel = NutritionUiModel(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class NutritionUpdateViewModel @Inject constructor(
    getNutritionUseCase: GetNutritionUseCase,
    private val updateNutritionUseCase: UpdateNutritionUseCase,
    private val deleteNutritionUseCase: DeleteNutritionUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : NutritionComponentViewModel() {

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
        .map { nutriments -> nutriments.map { nutritionConverter.entity(it).toUiModel() } }
        .cachedIn(viewModelScope)

    fun selectItem(nutritionUiModel: NutritionUiModel) {
        // TODO: Error Handling
        _uiState.update { state -> state.copy(nutritionUiModel = nutritionUiModel) }

        viewModelScope.launch(dispatcher) {
            _uiComponentState.update { state ->
                state.copy(
                    nutritionUiModel = nutritionUiModel
                )
            }
        }
    }

    fun update() {
        viewModelScope.launch(dispatcher) {
            try {
                val isSuccess =
                    NutritionConverter().uiModel(_uiComponentState.value.nutritionUiModel)
                        .toEntity()
                        .let { updateNutritionUseCase(it) }

                updateNutritionUiState(isSuccess)
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message) }
            }
        }
    }

    private fun updateNutritionUiState(isSuccess: Boolean) {
        _uiState.apply {
            when (isSuccess) {
                true -> update { state -> state.copy(nutritionUiModel = _uiComponentState.value.nutritionUiModel) }
                false -> update { state -> state.copy(errorMessage = "Update failed") }
            }
        }
    }

    fun delete(nutritionUiModel: NutritionUiModel) {
        viewModelScope.launch(dispatcher) {
            deleteNutritionUseCase(nutritionConverter.uiModel(nutritionUiModel).toEntity())
        }
    }
}
