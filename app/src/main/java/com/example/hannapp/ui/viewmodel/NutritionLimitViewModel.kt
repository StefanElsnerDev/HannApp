package com.example.hannapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.hannapp.data.Message
import com.example.hannapp.data.model.MilkLimitReferenceUiModel
import com.example.hannapp.data.model.NutritionLimitReferenceUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

enum class NutritionReference {
    KCAL, PROTEIN, CARBOHYDRATES, FAT
}

enum class MilkReference {
    TOTAL, PRE_NIGHT, NIGHT
}

interface NutritionLimitContract :
    UnidirectionalViewModel<NutritionLimitContract.State, NutritionLimitContract.Event> {

    data class State(
        val nutritionLimitReferenceUiModel: NutritionLimitReferenceUiModel = NutritionLimitReferenceUiModel(),
        val milkLimitReferenceUiModel: MilkLimitReferenceUiModel = MilkLimitReferenceUiModel(),
        val isLoading: Boolean = false,
        val errorMessage: Message? = null
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
        }
    }

    private fun updateNutritionReference(nutritionReference: NutritionReference, value: String) {
        when (nutritionReference) {
            NutritionReference.KCAL -> {
                _state.update {
                    it.copy(
                        nutritionLimitReferenceUiModel = _state.value.nutritionLimitReferenceUiModel.copy(
                            kcal = value
                        )
                    )
                }
            }

            NutritionReference.PROTEIN -> {
                _state.update {
                    it.copy(
                        nutritionLimitReferenceUiModel = _state.value.nutritionLimitReferenceUiModel.copy(
                            protein = value
                        )
                    )
                }
            }

            NutritionReference.CARBOHYDRATES -> {
                _state.update {
                    it.copy(
                        nutritionLimitReferenceUiModel = _state.value.nutritionLimitReferenceUiModel.copy(
                            carbohydrates = value
                        )
                    )
                }
            }

            NutritionReference.FAT -> {
                _state.update {
                    it.copy(
                        nutritionLimitReferenceUiModel = _state.value.nutritionLimitReferenceUiModel.copy(
                            fat = value
                        )
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
                        milkLimitReferenceUiModel = _state.value.milkLimitReferenceUiModel.copy(
                            total = value
                        )
                    )
                }
            }

            MilkReference.PRE_NIGHT -> {
                _state.update {
                    it.copy(
                        milkLimitReferenceUiModel = _state.value.milkLimitReferenceUiModel.copy(
                            preNight = value
                        )
                    )
                }
            }

            MilkReference.NIGHT -> {
                _state.update {
                    it.copy(
                        milkLimitReferenceUiModel = _state.value.milkLimitReferenceUiModel.copy(
                            night = value
                        )
                    )
                }
            }
        }
    }
}
