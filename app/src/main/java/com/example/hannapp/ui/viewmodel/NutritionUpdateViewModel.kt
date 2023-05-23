package com.example.hannapp.ui.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.hannapp.R
import com.example.hannapp.data.Message
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.data.modul.IoDispatcher
import com.example.hannapp.domain.DeleteNutritionUseCase
import com.example.hannapp.domain.GetNutritionUseCase
import com.example.hannapp.domain.UpdateNutritionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NutritionUpdateUiState(
    val cachedNutritionUiModel: NutritionUiModel = NutritionUiModel(),
    val isLoading: Boolean = false,
    val errorMessage: Message? = null
)

@HiltViewModel
class NutritionUpdateViewModel @Inject constructor(
    getNutritionUseCase: GetNutritionUseCase,
    private val updateNutritionUseCase: UpdateNutritionUseCase,
    private val deleteNutritionUseCase: DeleteNutritionUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : NutritionComponentViewModel() {

    private val _uiState = MutableStateFlow(NutritionUpdateUiState(isLoading = true))
    val uiState: StateFlow<NutritionUpdateUiState> = _uiState.asStateFlow()

    val nutriments = getNutritionUseCase.getAll()
        .catch { throwable ->
            _uiState.update { state ->
                state.copy(
                    isLoading = false,
                    errorMessage = Message(
                        messageRes = null,
                        message = throwable.message
                    )
                )
            }
        }.cachedIn(viewModelScope)

    fun selectItem(nutritionUiModel: NutritionUiModel) {
        // TODO: Error Handling
        _uiState.update { state -> state.copy(cachedNutritionUiModel = nutritionUiModel) }

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
                    _uiComponentState.value.nutritionUiModel.let { updateNutritionUseCase(it) }

                updateNutritionUiState(isSuccess)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = Message(
                            messageRes = null,
                            message = e.message
                        )
                    )
                }
            }
        }
    }

    private fun updateNutritionUiState(isSuccess: Boolean) {
        _uiState.apply {
            when (isSuccess) {
                true -> update { state ->
                    state.copy(
                        cachedNutritionUiModel = _uiComponentState.value.nutritionUiModel
                    )
                }

                false -> update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = Message(
                            messageRes = R.string.nutriment_update_failed,
                            message = null
                        )
                    )
                }
            }
        }
    }

    fun delete(nutritionUiModel: NutritionUiModel) {
        viewModelScope.launch(dispatcher) {
            deleteNutritionUseCase(nutritionUiModel = nutritionUiModel)
        }
    }
}
