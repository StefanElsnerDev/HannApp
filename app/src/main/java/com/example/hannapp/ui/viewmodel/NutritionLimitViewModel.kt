package com.example.hannapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hannapp.data.Message
import com.example.hannapp.data.model.MilkLimitReferenceUiModel
import com.example.hannapp.data.model.NutritionLimitReferenceUiModel
import com.example.hannapp.domain.GetMilkQuantityReferencesUseCase
import com.example.hannapp.domain.GetNutritionReferencesUseCase
import com.example.hannapp.domain.SaveMilkQuantityReferencesUseCase
import com.example.hannapp.domain.SaveNutritionReferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

interface NutritionLimitContract :
    UnidirectionalViewModel<NutritionLimitContract.State, NutritionLimitContract.Event> {

    sealed class ReferenceState {
        data class State(
            val value: String = "",
            val isError: Boolean = false
        ) {
            fun validate(
                referenceValidationStrategy: ReferenceValidationStrategy,
                reference: Reference
            ) {
                referenceValidationStrategy.validate(value, reference)
            }
        }
    }

    data class State(
        val kcal: ReferenceState.State = ReferenceState.State(),
        val protein: ReferenceState.State = ReferenceState.State(),
        val carbohydrates: ReferenceState.State = ReferenceState.State(),
        val fat: ReferenceState.State = ReferenceState.State(),
        val totalQuantity: ReferenceState.State = ReferenceState.State(),
        val preNightQuantity: ReferenceState.State = ReferenceState.State(),
        val nightQuantity: ReferenceState.State = ReferenceState.State(),
        val isLoading: Boolean = false,
        val errorMessage: Message? = null,
        val invalidReferences: List<Reference> = emptyList(),
        val isDataValid: Boolean = false
    )

    sealed class Event {
        data class OnNutritionUpdate(
            val nutritionReference: NutritionReference,
            val value: String
        ) : Event()

        data class OnMilkUpdate(
            val milkReference: MilkReference,
            val value: String
        ) : Event()

        object OnValidate : Event()

        object OnSave : Event()
    }
}

@HiltViewModel
class NutritionLimitViewModel @Inject constructor(
    private val saveNutritionReferencesUseCase: SaveNutritionReferencesUseCase,
    private val saveMilkQuantityReferencesUseCase: SaveMilkQuantityReferencesUseCase,
    private val getNutritionReferencesUseCase: GetNutritionReferencesUseCase,
    private val getMilkQuantityReferencesUseCase: GetMilkQuantityReferencesUseCase
) : ViewModel(), NutritionLimitContract {
    private val _state = MutableStateFlow(NutritionLimitContract.State(isLoading = true))
    override val state: StateFlow<NutritionLimitContract.State> = _state.asStateFlow()

    override fun event(event: NutritionLimitContract.Event) {
        when (event) {
            is NutritionLimitContract.Event.OnNutritionUpdate -> updateNutritionReference(
                event.nutritionReference,
                event.value
            )

            is NutritionLimitContract.Event.OnMilkUpdate -> updateMilkReference(
                event.milkReference,
                event.value
            )

            is NutritionLimitContract.Event.OnValidate -> {
                emitEmptyReferences()
                validate()
            }

            is NutritionLimitContract.Event.OnSave -> {
                saveNutritionReferences()
                saveMilkQuantityReferences()
            }
        }
    }

    private fun updateNutritionReference(nutritionReference: NutritionReference, value: String) {
        when (nutritionReference) {
            NutritionReference.KCAL -> {
                _state.update {
                    it.copy(
                        kcal = NutritionLimitContract.ReferenceState.State(value = value)
                    )
                }
            }

            NutritionReference.PROTEIN -> {
                _state.update {
                    it.copy(
                        protein = NutritionLimitContract.ReferenceState.State(value = value)
                    )
                }
            }

            NutritionReference.CARBOHYDRATES -> {
                _state.update {
                    it.copy(
                        carbohydrates = NutritionLimitContract.ReferenceState.State(value = value)
                    )
                }
            }

            NutritionReference.FAT -> {
                _state.update {
                    it.copy(
                        fat = NutritionLimitContract.ReferenceState.State(value = value)
                    )
                }
            }
        }
    }

    private fun updateMilkReference(milkReference: MilkReference, value: String) {
        when (milkReference) {
            MilkReference.TOTAL -> {
                _state.update {
                    it.copy(
                        totalQuantity = NutritionLimitContract.ReferenceState.State(value = value)
                    )
                }
            }

            MilkReference.PRE_NIGHT -> {
                _state.update {
                    it.copy(
                        preNightQuantity = NutritionLimitContract.ReferenceState.State(value = value)
                    )
                }
            }

            MilkReference.NIGHT -> {
                _state.update {
                    it.copy(
                        nightQuantity = NutritionLimitContract.ReferenceState.State(value = value)
                    )
                }
            }
        }
    }

    private fun emitEmptyReferences() {
        val invalids = mutableListOf<Reference>()
        _state.value.apply {
            kcal.validate(
                referenceValidationStrategy = InvalidReferencesList(invalids),
                reference = NutritionReference.KCAL
            )
            protein.validate(
                referenceValidationStrategy = InvalidReferencesList(invalids),
                reference = NutritionReference.PROTEIN
            )
            carbohydrates.validate(
                referenceValidationStrategy = InvalidReferencesList(invalids),
                reference = NutritionReference.CARBOHYDRATES
            )
            fat.validate(
                referenceValidationStrategy = InvalidReferencesList(invalids),
                reference = NutritionReference.FAT
            )
            totalQuantity.validate(
                referenceValidationStrategy = InvalidReferencesList(invalids),
                reference = MilkReference.TOTAL
            )
            preNightQuantity.validate(
                referenceValidationStrategy = InvalidReferencesList(invalids),
                reference = MilkReference.PRE_NIGHT
            )
            nightQuantity.validate(
                referenceValidationStrategy = InvalidReferencesList(invalids),
                reference = MilkReference.NIGHT
            )
        }
        _state.update { it.copy(invalidReferences = invalids.toList()) }
    }

    private fun validate() = _state.update { it.copy(isDataValid = it.invalidReferences.isEmpty()) }

    private fun saveNutritionReferences() {
        viewModelScope.launch {
            try {
                _state.value.apply {
                    saveNutritionReferencesUseCase(
                        NutritionLimitReferenceUiModel(
                            kcal = kcal.value,
                            protein = protein.value,
                            carbohydrates = carbohydrates.value,
                            fat = fat.value
                        )
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = Message(
                            messageRes = null,
                            message = e.message
                        )
                    )
                }
            }
        }
    }

    private fun saveMilkQuantityReferences() {
        viewModelScope.launch {
            try {
                _state.value.apply {
                    saveMilkQuantityReferencesUseCase(
                        MilkLimitReferenceUiModel(
                            total = totalQuantity.value,
                            day = null,
                            preNight = preNightQuantity.value,
                            night = nightQuantity.value
                        )
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = Message(
                            messageRes = null,
                            message = e.message
                        )
                    )
                }
            }
        }
    }

    private fun getNutritionReferences() {
        viewModelScope.launch {
            try {
                getNutritionReferencesUseCase().collectLatest { model ->
                    _state.update {
                        it.copy(
                            kcal = NutritionLimitContract.ReferenceState.State(
                                value = model.kcal
                            ),
                            protein = NutritionLimitContract.ReferenceState.State(
                                value = model.protein
                            ),
                            carbohydrates = NutritionLimitContract.ReferenceState.State(
                                value = model.carbohydrates
                            ),
                            fat = NutritionLimitContract.ReferenceState.State(
                                value = model.fat
                            )
                        )
                    }
                }
            } catch (e: java.lang.Exception) {
                _state.update {
                    it.copy(
                        errorMessage = Message(
                            messageRes = null,
                            message = e.message
                        )
                    )
                }
            }
        }
    }

    private fun getMilkReferences() {
        viewModelScope.launch {
            try {
                getMilkQuantityReferencesUseCase().collectLatest { model ->
                    _state.update {
                        it.copy(
                            totalQuantity = NutritionLimitContract.ReferenceState.State(
                                value = model.total
                            ),
                            preNightQuantity = NutritionLimitContract.ReferenceState.State(
                                value = model.preNight
                            ),
                            nightQuantity = NutritionLimitContract.ReferenceState.State(
                                value = model.night
                            )
                        )
                    }
                }
            } catch (e: java.lang.Exception) {
                _state.update {
                    it.copy(
                        errorMessage = Message(
                            messageRes = null,
                            message = e.message
                        )
                    )
                }
            }
        }
    }

    init {
        getNutritionReferences()
        getMilkReferences()
    }
}
