package com.example.hannapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.hannapp.data.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
    }
}

@HiltViewModel
class NutritionLimitViewModel @Inject constructor() : ViewModel(), NutritionLimitContract {
    // private save(uiLimitModel) = useCase(uiLimitModel) // use case transforms to to ReferenceModel with require

    // private save(milkUiModel) = useCase(milkUiModel) // use case transforms to to MilkModel with require
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
}
