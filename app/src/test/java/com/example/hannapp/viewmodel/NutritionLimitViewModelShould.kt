package com.example.hannapp.viewmodel

import com.example.hannapp.data.model.MilkLimitReferenceUiModel
import com.example.hannapp.data.model.NutritionLimitReferenceUiModel
import com.example.hannapp.ui.viewmodel.MilkReference
import com.example.hannapp.ui.viewmodel.NutritionLimitContract
import com.example.hannapp.ui.viewmodel.NutritionLimitViewModel
import com.example.hannapp.ui.viewmodel.NutritionReference
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class NutritionLimitViewModelShould {

    private lateinit var nutritionLimitViewModel: NutritionLimitViewModel

    @BeforeEach
    fun beforeEach() {
        nutritionLimitViewModel = NutritionLimitViewModel()
    }

    @Test
    fun emitUiStateWithReferencesAndLoadingOnInit() {
        assertThat(nutritionLimitViewModel.state.value.nutritionLimitReferenceUiModel).isEqualTo(
            NutritionLimitReferenceUiModel()
        )
        assertThat(nutritionLimitViewModel.state.value.milkLimitReferenceUiModel).isEqualTo(
            MilkLimitReferenceUiModel()
        )
        assertThat(nutritionLimitViewModel.state.value.errorMessage).isNull()
        assertThat(nutritionLimitViewModel.state.value.isLoading).isTrue
    }

    @Nested
    inner class UpdateReferences {

        private val stringValue = "112.0"

        @Test
        fun updateKcal() {
            nutritionLimitViewModel.event(
                NutritionLimitContract.Event.OnNutritionUpdate(
                    nutritionReference = NutritionReference.KCAL,
                    value = stringValue
                )
            )

            assertThat(nutritionLimitViewModel.state.value.nutritionLimitReferenceUiModel.kcal).isEqualTo(
                stringValue
            )
        }

        @Test
        fun updateProtein() {
            nutritionLimitViewModel.event(
                NutritionLimitContract.Event.OnNutritionUpdate(
                    nutritionReference = NutritionReference.PROTEIN,
                    value = stringValue
                )
            )

            assertThat(nutritionLimitViewModel.state.value.nutritionLimitReferenceUiModel.protein).isEqualTo(
                stringValue
            )
        }

        @Test
        fun updateCarbohydrates() {
            nutritionLimitViewModel.event(
                NutritionLimitContract.Event.OnNutritionUpdate(
                    nutritionReference = NutritionReference.CARBOHYDRATES,
                    value = stringValue
                )
            )

            assertThat(nutritionLimitViewModel.state.value.nutritionLimitReferenceUiModel.carbohydrates).isEqualTo(
                stringValue
            )
        }

        @Test
        fun updateFat() {
            nutritionLimitViewModel.event(
                NutritionLimitContract.Event.OnNutritionUpdate(
                    nutritionReference = NutritionReference.FAT,
                    value = stringValue
                )
            )

            assertThat(nutritionLimitViewModel.state.value.nutritionLimitReferenceUiModel.fat).isEqualTo(
                stringValue
            )
        }
    }

    @Nested
    inner class UpdateMilkQuantities {

        private val stringValue = "123.0"

        @Test
        fun updateTotalQuantity() {
            nutritionLimitViewModel.event(
                NutritionLimitContract.Event.OnMilkUpdate(
                    milkReference = MilkReference.TOTAL,
                    value = stringValue
                )
            )

            assertThat(nutritionLimitViewModel.state.value.milkLimitReferenceUiModel.total).isEqualTo(
                stringValue
            )
        }

        @Test
        fun updatePreNightQuantity() {
            nutritionLimitViewModel.event(
                NutritionLimitContract.Event.OnMilkUpdate(
                    milkReference = MilkReference.PRE_NIGHT,
                    value = stringValue
                )
            )

            assertThat(nutritionLimitViewModel.state.value.milkLimitReferenceUiModel.preNight).isEqualTo(
                stringValue
            )
        }

        @Test
        fun updateNightQuantity() {
            nutritionLimitViewModel.event(
                NutritionLimitContract.Event.OnMilkUpdate(
                    milkReference = MilkReference.NIGHT,
                    value = stringValue
                )
            )

            assertThat(nutritionLimitViewModel.state.value.milkLimitReferenceUiModel.night).isEqualTo(
                stringValue
            )
        }
    }
}
