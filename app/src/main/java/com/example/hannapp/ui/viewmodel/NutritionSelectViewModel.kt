package com.example.hannapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.hannapp.data.model.NutrimentUiLogModel
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.data.model.convert.NutritionConverter
import com.example.hannapp.data.model.entity.Nutrition
import com.example.hannapp.data.modul.IoDispatcher
import com.example.hannapp.domain.DeleteNutrimentLogUseCase
import com.example.hannapp.domain.GetNutrimentLogUseCase
import com.example.hannapp.domain.GetNutritionUseCase
import com.example.hannapp.domain.InsertNutrimentLogUseCase
import com.example.hannapp.domain.UpdateNutrimentLogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NutritionUiState(
    var nutritionUiModel: NutritionUiModel = NutritionUiModel(),
    val nutrimentLogId: Long? = null,
    var quantity: String = "",
    var isSelectionValid: Boolean = false,
    val isEditMode: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class NutritionSelectViewModel @Inject constructor(
    private val getNutritionUseCase: GetNutritionUseCase,
    private val insertNutrimentLogUseCase: InsertNutrimentLogUseCase,
    private val deleteNutrimentLogUseCase: DeleteNutrimentLogUseCase,
    private val updateNutrimentLogUseCase: UpdateNutrimentLogUseCase,
    getNutrimentLogUseCase: GetNutrimentLogUseCase,
    private val nutritionConverter: NutritionConverter,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private lateinit var memento: Memento

    private val _uiState = MutableStateFlow(NutritionUiState(isLoading = true))
    val uiState: StateFlow<NutritionUiState> = _uiState.asStateFlow()

    private var _nutriments = MutableSharedFlow<PagingData<Nutrition>>()
    val nutriments = _nutriments
        .map { nutriments -> nutriments.map { nutritionConverter.entity(it).toUiModel() } }
        .cachedIn(viewModelScope)

    val nutrimentLog: StateFlow<List<NutrimentUiLogModel>> = getNutrimentLogUseCase.observeNutrimentLog()
        .catch {
            _uiState.update { state ->
                state.copy(
                    isLoading = false,
                    errorMessage = it.message
                )
            }
        }.map { list ->
            list.map {
                NutrimentUiLogModel(
                    id = it.id,
                    nutrition = nutritionConverter.entity(it.nutrition).toUiModel(),
                    quantity = it.quantity,
                    unit = "",
                    timeStamp = it.createdAt
                )
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        )

    fun getAll() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch(dispatcher) {
            getNutritionUseCase.getAll()
                .catch { throwable ->
                    updateErrorState(throwable.message ?: "Loading of nutriments from database failed")
                }
                .collectLatest { nutriments ->
                    _nutriments.emit(nutriments)
                }
        }
    }

    fun select(nutritionUiModel: NutritionUiModel) {
        nutritionUiModel.apply {
            when (id) {
                null -> updateErrorState("Invalid selection")
                else -> _uiState.update { state ->
                    state.copy(
                        nutritionUiModel = this
                    )
                }
            }
        }
        validateSelection()
    }

    fun setQuantity(quantity: String) = _uiState.update { it.copy(quantity = quantity) }

    fun add() {
        val quantity = _uiState.value.quantity
        val id = _uiState.value.nutritionUiModel.id

        require(id != null) {"Invalid ID"}

        viewModelScope.launch(dispatcher) {
            try {
                val isInserted = insertNutrimentLogUseCase(
                    nutrimentId = id,
                    quantity = quantity.toDouble(),
                )

                require(isInserted) { "Insertion of log entry failed" }
            } catch (e: IllegalArgumentException) {
                updateErrorState(e.message ?: "Illegal Argument")
            } catch (e: NumberFormatException) {
                updateErrorState("Invalid Input")
            } catch (e: Exception) {
                updateErrorState(e.message ?: "Addition of log entry failed")
            }
        }
        _uiState.update { it.copy(quantity = "") }
    }

    fun edit(nutrimentUiLogModel: NutrimentUiLogModel) {
        memento = Memento(_uiState.value.nutritionUiModel, _uiState.value.quantity)

        _uiState.update {
            it.copy(
                nutritionUiModel = nutrimentUiLogModel.nutrition,
                nutrimentLogId = nutrimentUiLogModel.id,
                quantity = nutrimentUiLogModel.quantity.toString(),
                isEditMode = true
            )
        }
        validateSelection()
    }

    fun update() {
        viewModelScope.launch(dispatcher) {
            try {
                val logId = _uiState.value.nutrimentLogId
                require(logId != null) {"Invalid Log-ID"}

                val nutrimentId = _uiState.value.nutritionUiModel.id
                require(nutrimentId != null) {"Invalid Nutriment-ID"}

                updateNutrimentLogUseCase.update(
                    logId = logId,
                    nutrimentId = nutrimentId,
                    quantity = _uiState.value.quantity.toDouble(),
                )

                _uiState.update { it.copy(isEditMode = false) }
            } catch (e: NumberFormatException) {
                updateErrorState(e.message ?: "Missing quantity")
            } catch (e: Exception) {
                updateErrorState("Editing failed")
            }
        }
    }

    fun abort() {
        _uiState.update { state ->
            state.copy(
                nutritionUiModel = memento.nutritionUiModel,
                quantity = memento.quantity,
                isEditMode = false
            )
        }
        validateSelection()
    }

    fun castAsDouble(value: String, function: (Double)->Unit) {
        val nullableValue = value.toDoubleOrNull()
        if(nullableValue != null){
            nullableValue.apply(function)
        } else {
            _uiState.update { state ->
                state.copy(
                    isLoading = false,
                    errorMessage = "Invalid Input"
                )
            }
        }
    }

    private fun updateErrorState(message: String) {
        _uiState.update { state ->
            state.copy(
                isLoading = false,
                errorMessage = message
            )
        }
    }

    fun clearAll() {
        clearHistory()
        unselect()
        validateSelection()
    }

    private fun clearHistory(){
        viewModelScope.launch(dispatcher) {
            try {
                val isCleared = deleteNutrimentLogUseCase.clear()

                if (!isCleared) updateErrorState("Deletion failed")
            } catch (e: Exception) {
                updateErrorState(e.message ?: "Unexpected error on deletion")
            }
        }

    }

    private fun unselect(){
        _uiState.value.nutritionUiModel = NutritionUiModel()
    }

    private fun validateSelection(){
        _uiState.update { state ->
            state.copy(
                isSelectionValid = state.nutritionUiModel.id != null
            )
        }
    }

    inner class Memento(
        val nutritionUiModel: NutritionUiModel,
        val quantity: String
    )
}
