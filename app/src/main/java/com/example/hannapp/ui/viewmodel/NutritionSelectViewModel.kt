package com.example.hannapp.ui.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.hannapp.R
import com.example.hannapp.data.Message
import com.example.hannapp.data.model.NutrimentUiLogModel
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.data.modul.IoDispatcher
import com.example.hannapp.domain.DeleteNutrimentLogUseCase
import com.example.hannapp.domain.GetNutrimentLogUseCase
import com.example.hannapp.domain.GetNutritionUseCase
import com.example.hannapp.domain.GetPreNightMaltoSubstitutionUseCase
import com.example.hannapp.domain.GetPreNightMilkDiscardUseCase
import com.example.hannapp.domain.InsertNutrimentLogUseCase
import com.example.hannapp.domain.UpdateNutrimentLogUseCase
import com.example.hannapp.domain.ValidatePreNightNutritionLogUseCase
import com.example.hannapp.ui.mood.Mood
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

interface NutrimentSelectContract :
    UnidirectionalViewModel<NutrimentSelectContract.State, NutrimentSelectContract.Event> {

    data class State(
        val nutritionUiModel: NutritionUiModel = NutritionUiModel(),
        val log: List<NutrimentUiLogModel> = emptyList(),
        val nutrimentLogId: Long? = null,
        val quantity: String = "",
        val validation: Mood = Mood.GREEN,
        val milkDiscard: String = "",
        val maltoSubstitution: String = "",
        val isEditMode: Boolean = false,
        val isLoading: Boolean = false,
        val errorMessage: Message? = null
    )
    sealed class Event {
        object OnGetAll : Event()
        data class OnSelect(val nutritionUiModel: NutritionUiModel) : Event()
        data class OnSetQuantity(val quantity: String) : Event()
        data class OnEdit(val nutrimentUiLogModel: NutrimentUiLogModel) : Event()
        object OnAdd : Event()
        object OnUpdate : Event()
        object OnAbort : Event()
        object OnClearAll : Event()
        object OnValidate : Event()
    }
}

@HiltViewModel
class NutritionSelectViewModel @Inject constructor(
    private val getNutritionUseCase: GetNutritionUseCase,
    private val insertNutrimentLogUseCase: InsertNutrimentLogUseCase,
    private val deleteNutrimentLogUseCase: DeleteNutrimentLogUseCase,
    private val updateNutrimentLogUseCase: UpdateNutrimentLogUseCase,
    private val getNutrimentLogUseCase: GetNutrimentLogUseCase,
    private val validatePreNightNutritionLogUseCase: ValidatePreNightNutritionLogUseCase,
    private val getPreNightMilkDiscardUseCase: GetPreNightMilkDiscardUseCase,
    private val getPreNightMaltoSubstitutionUseCase: GetPreNightMaltoSubstitutionUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel(), NutrimentSelectContract {
    private lateinit var memento: Memento

    private val _uiState = MutableStateFlow(NutrimentSelectContract.State(isLoading = true))
    override val state: StateFlow<NutrimentSelectContract.State> = _uiState.asStateFlow()

    init {
        getLog()
        validate()
        getMilkOverflow()
        getMaltoSubstitution()
    }

    private var _nutriments = MutableSharedFlow<PagingData<NutritionUiModel>>()
    val nutriments = _nutriments.cachedIn(viewModelScope)

    override fun event(event: NutrimentSelectContract.Event) {
        when (event) {
            is NutrimentSelectContract.Event.OnSelect -> select(event.nutritionUiModel)
            is NutrimentSelectContract.Event.OnAbort -> abort()
            is NutrimentSelectContract.Event.OnClearAll -> clearAll()
            is NutrimentSelectContract.Event.OnAdd -> add()
            is NutrimentSelectContract.Event.OnEdit -> edit(event.nutrimentUiLogModel)
            is NutrimentSelectContract.Event.OnUpdate -> update()
            is NutrimentSelectContract.Event.OnGetAll -> getAll()
            is NutrimentSelectContract.Event.OnSetQuantity -> setQuantity(event.quantity)
            is NutrimentSelectContract.Event.OnValidate -> validate()
        }
    }

    private fun getLog() {
        viewModelScope.launch(dispatcher) {
            getNutrimentLogUseCase()
                .catch {
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            errorMessage = Message(
                                messageRes = null,
                                message = it.message
                            )
                        )
                    }
                }.collectLatest { log ->
                    _uiState.update { it.copy(log = log) }
                }
        }
    }

    private fun getAll() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch(dispatcher) {
            getNutritionUseCase.getAll()
                .catch { throwable ->
                    updateErrorState(
                        stringRes = R.string.loading_nutriments_failed,
                        string = throwable.message
                    )
                }.collectLatest { nutriments ->
                    _nutriments.emit(nutriments)
                }
        }
    }

    private fun select(nutritionUiModel: NutritionUiModel) {
        nutritionUiModel.apply {
            when (id) {
                null -> {
                    updateErrorState(
                        stringRes = R.string.invalid_selection,
                        string = null
                    )
                }

                else -> _uiState.update { state ->
                    state.copy(
                        nutritionUiModel = this
                    )
                }
            }
        }
    }

    private fun setQuantity(quantity: String) = _uiState.update { it.copy(quantity = quantity) }

    private fun add() {
        val quantity = _uiState.value.quantity
        val id = _uiState.value.nutritionUiModel.id

        viewModelScope.launch(dispatcher) {
            try {
                require(id != null) { R.string.invalid_nutriment_id }

                val isInserted = insertNutrimentLogUseCase(
                    nutrimentId = id,
                    quantity = quantity.toDouble()
                )

                require(isInserted) { R.string.insertion_failed }
            } catch (e: IllegalArgumentException) {
                updateErrorState(
                    stringRes = e.message?.toIntOrNull(),
                    string = e.message
                )
            } catch (e: NumberFormatException) {
                updateErrorState(
                    stringRes = R.string.invalid_input,
                    string = e.message
                )
            } catch (e: Exception) {
                updateErrorState(
                    stringRes = R.string.log_failed,
                    string = e.message
                )
            }
        }
        _uiState.update { it.copy(quantity = "") }
    }

    private fun edit(nutrimentUiLogModel: NutrimentUiLogModel) {
        memento = Memento(_uiState.value.nutritionUiModel, _uiState.value.quantity)

        _uiState.update {
            it.copy(
                nutritionUiModel = nutrimentUiLogModel.nutrition,
                nutrimentLogId = nutrimentUiLogModel.id,
                quantity = nutrimentUiLogModel.quantity.toString(),
                isEditMode = true
            )
        }
    }

    private fun update() {
        viewModelScope.launch(dispatcher) {
            try {
                val logId = _uiState.value.nutrimentLogId
                require(logId != null) { R.string.invalid_log_id }

                val nutrimentId = _uiState.value.nutritionUiModel.id
                require(nutrimentId != null) { R.string.invalid_nutriment_id }

                updateNutrimentLogUseCase.update(
                    logId = logId,
                    nutrimentId = nutrimentId,
                    quantity = _uiState.value.quantity.toDouble()
                )

                _uiState.update { it.copy(isEditMode = false) }
            } catch (e: IllegalArgumentException) {
                updateErrorState(
                    stringRes = e.message?.toIntOrNull(),
                    string = e.message
                )
            } catch (e: NumberFormatException) {
                updateErrorState(
                    stringRes = R.string.missing_quantity,
                    string = e.message
                )
            } catch (e: Exception) {
                updateErrorState(
                    stringRes = R.string.editing_failed,
                    string = e.message
                )
            }
        }
    }

    private fun abort() {
        _uiState.update { state ->
            state.copy(
                nutritionUiModel = memento.nutritionUiModel,
                quantity = memento.quantity,
                isEditMode = false
            )
        }
    }

    private fun updateErrorState(@StringRes stringRes: Int?, string: String? = null) {
        _uiState.update { state ->
            state.copy(
                isLoading = false,
                errorMessage = Message(
                    messageRes = stringRes,
                    message = string
                )
            )
        }
    }

    private fun clearAll() {
        clearHistory()
        unselect()
    }

    private fun clearHistory() {
        viewModelScope.launch(dispatcher) {
            try {
                val isCleared = deleteNutrimentLogUseCase.clear()

                if (!isCleared) {
                    updateErrorState(
                        stringRes = R.string.deletion_failed
                    )
                }
            } catch (e: Exception) {
                updateErrorState(
                    stringRes = R.string.unexpected_error_on_deletion,
                    string = e.message
                )
            }
        }
    }

    private fun unselect() {
        _uiState.update { state -> state.copy(NutritionUiModel()) }
    }

    private fun validate() {
        viewModelScope.launch(dispatcher) {
            try {
                validatePreNightNutritionLogUseCase().collectLatest { mood ->
                    _uiState.update { it.copy(validation = mood) }
                }
            } catch (e: IllegalArgumentException) {
                updateErrorState(
                    stringRes = R.string.missing_nutrition_limits,
                    string = e.message
                )
            } catch (e: Exception) {
                updateErrorState(
                    stringRes = null,
                    string = e.message
                )
            }
        }
    }

    private fun getMilkOverflow() {
        viewModelScope.launch(dispatcher) {
            try {
                getPreNightMilkDiscardUseCase().collectLatest { milk ->
                    _uiState.update { it.copy(milkDiscard = milk.toString()) }
                }
            } catch (e: Exception) {
                updateErrorState(
                    stringRes = null,
                    string = e.message
                )
            }
        }
    }

    private fun getMaltoSubstitution() {
        viewModelScope.launch(dispatcher) {
            try {
                getPreNightMaltoSubstitutionUseCase().collectLatest { malto ->
                    _uiState.update { it.copy(maltoSubstitution = malto.toString()) }
                }
            } catch (e: Exception) {
                updateErrorState(
                    stringRes = null,
                    string = e.message
                )
            }
        }
    }

    private inner class Memento(
        val nutritionUiModel: NutritionUiModel,
        val quantity: String
    )
}
