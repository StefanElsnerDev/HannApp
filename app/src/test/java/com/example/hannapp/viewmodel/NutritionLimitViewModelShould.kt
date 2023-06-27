package com.example.hannapp.viewmodel

import com.example.hannapp.data.model.MilkLimitReferenceUiModel
import com.example.hannapp.data.model.NutritionLimitReferenceUiModel
import com.example.hannapp.domain.GetMilkQuantityReferencesUseCase
import com.example.hannapp.domain.GetNutritionReferencesUseCase
import com.example.hannapp.domain.SaveMilkQuantityReferencesUseCase
import com.example.hannapp.domain.SaveNutritionReferencesUseCase
import com.example.hannapp.ui.viewmodel.MilkReference
import com.example.hannapp.ui.viewmodel.NutritionLimitContract
import com.example.hannapp.ui.viewmodel.NutritionLimitViewModel
import com.example.hannapp.ui.viewmodel.NutritionReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.clearInvocations
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class NutritionLimitViewModelShould {

    private lateinit var nutritionLimitViewModel: NutritionLimitViewModel

    private val testDispatcher = UnconfinedTestDispatcher()
    private val saveNutritionReferencesUseCase = mock<SaveNutritionReferencesUseCase>()
    private val saveMilkQuantityReferencesUseCase = mock<SaveMilkQuantityReferencesUseCase>()
    private val getNutritionReferencesUseCase = mock<GetNutritionReferencesUseCase>()
    private val getMilkQuantityReferencesUseCase = mock<GetMilkQuantityReferencesUseCase>()

    @BeforeEach
    fun beforeEach() = runTest {
        Dispatchers.setMain(testDispatcher)

        whenever(saveNutritionReferencesUseCase.invoke(any())).thenReturn(Unit)
        whenever(saveMilkQuantityReferencesUseCase.invoke(any())).thenReturn(Unit)
        whenever(getNutritionReferencesUseCase.invoke()).thenReturn(flowOf())
        whenever(getMilkQuantityReferencesUseCase.invoke()).thenReturn(flowOf())

        nutritionLimitViewModel = NutritionLimitViewModel(
            saveNutritionReferencesUseCase = saveNutritionReferencesUseCase,
            saveMilkQuantityReferencesUseCase = saveMilkQuantityReferencesUseCase,
            getNutritionReferencesUseCase = getNutritionReferencesUseCase,
            getMilkQuantityReferencesUseCase = getMilkQuantityReferencesUseCase
        )
    }

    @AfterEach
    fun afterEach() {
        Dispatchers.resetMain()
    }

    @Test
    fun emitUiStateWithReferencesAndLoadingOnInit() {
        assertThat(nutritionLimitViewModel.state.value.kcal.value).isEqualTo("")
        assertThat(nutritionLimitViewModel.state.value.protein.value).isEqualTo("")
        assertThat(nutritionLimitViewModel.state.value.carbohydrates.value).isEqualTo("")
        assertThat(nutritionLimitViewModel.state.value.fat.value).isEqualTo("")
        assertThat(nutritionLimitViewModel.state.value.totalQuantity.value).isEqualTo("")
        assertThat(nutritionLimitViewModel.state.value.preNightQuantity.value).isEqualTo("")
        assertThat(nutritionLimitViewModel.state.value.nightQuantity.value).isEqualTo("")

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

            assertThat(nutritionLimitViewModel.state.value.kcal.value).isEqualTo(
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

            assertThat(nutritionLimitViewModel.state.value.protein.value).isEqualTo(
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

            assertThat(nutritionLimitViewModel.state.value.carbohydrates.value).isEqualTo(
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

            assertThat(nutritionLimitViewModel.state.value.fat.value).isEqualTo(
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

            assertThat(nutritionLimitViewModel.state.value.totalQuantity.value).isEqualTo(
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

            assertThat(nutritionLimitViewModel.state.value.preNightQuantity.value).isEqualTo(
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

            assertThat(nutritionLimitViewModel.state.value.nightQuantity.value).isEqualTo(
                stringValue
            )
        }
    }

    @Nested
    inner class ValidateOnInput {

        private val nutritionReferences = mapOf(
            NutritionReference.KCAL to "1",
            NutritionReference.PROTEIN to "2",
            NutritionReference.CARBOHYDRATES to "3",
            NutritionReference.FAT to "4"
        )

        private val milkReferences = mapOf(
            MilkReference.TOTAL to "200",
            MilkReference.PRE_NIGHT to "20",
            MilkReference.NIGHT to "80"
        )

        @BeforeEach
        fun beforeEach() {
            nutritionReferences.forEach {
                nutritionLimitViewModel.event(
                    NutritionLimitContract.Event.OnNutritionUpdate(
                        it.key,
                        it.value
                    )
                )
            }

            milkReferences.forEach {
                nutritionLimitViewModel.event(
                    NutritionLimitContract.Event.OnMilkUpdate(
                        it.key,
                        it.value
                    )
                )
            }
        }

        @Test
        fun emitEmptyFieldsAndValidation() {
            nutritionLimitViewModel.event(
                NutritionLimitContract.Event.OnNutritionUpdate(
                    NutritionReference.KCAL,
                    ""
                )
            )
            nutritionLimitViewModel.event(
                NutritionLimitContract.Event.OnMilkUpdate(
                    MilkReference.TOTAL,
                    ""
                )
            )
            nutritionLimitViewModel.event(
                NutritionLimitContract.Event.OnMilkUpdate(
                    MilkReference.PRE_NIGHT,
                    ""
                )
            )

            assertThat(nutritionLimitViewModel.state.value.isDataValid).isFalse
            assertThat(nutritionLimitViewModel.state.value.invalidReferences).isEqualTo(
                listOf(
                    NutritionReference.KCAL,
                    MilkReference.TOTAL,
                    MilkReference.PRE_NIGHT
                )
            )
        }

        @Test
        fun emitInvalidFieldsAndValidation() {
            assertThat(nutritionLimitViewModel.state.value.isDataValid).isTrue

            nutritionLimitViewModel.event(
                NutritionLimitContract.Event.OnNutritionUpdate(
                    NutritionReference.KCAL,
                    "12?45"
                )
            )

            nutritionLimitViewModel.event(
                NutritionLimitContract.Event.OnNutritionUpdate(
                    NutritionReference.PROTEIN,
                    "protein%&§"
                )
            )

            assertThat(nutritionLimitViewModel.state.value.isDataValid).isFalse
            assertThat(nutritionLimitViewModel.state.value.invalidReferences).isEqualTo(
                listOf(
                    NutritionReference.KCAL,
                    NutritionReference.PROTEIN
                )
            )
        }
    }

    @Nested
    inner class SaveInput {

        private val errorMessage = "Something unexpected happened."

        @Test
        fun invokeSaveNutritionUseCase() = runTest {
            nutritionLimitViewModel.event(NutritionLimitContract.Event.OnSave)

            verify(saveNutritionReferencesUseCase).invoke(any())
        }

        @Test
        fun invokeSaveMilkReferencesUseCase() = runTest {
            nutritionLimitViewModel.event(NutritionLimitContract.Event.OnSave)

            verify(saveMilkQuantityReferencesUseCase).invoke(any())
        }

        @Test
        fun emitErrorOnFailingSaveNutritionReferences() = runTest {
            whenever(saveNutritionReferencesUseCase.invoke(any())).thenThrow(RuntimeException(errorMessage))

            nutritionLimitViewModel.event(NutritionLimitContract.Event.OnSave)

            assertThat(nutritionLimitViewModel.state.value.errorMessage?.messageRes).isNull()
            assertThat(nutritionLimitViewModel.state.value.errorMessage?.message).isEqualTo(
                errorMessage
            )
        }

        @Test
        fun emitErrorOnFailingSaveMilkQuantities() = runTest {
            whenever(saveMilkQuantityReferencesUseCase.invoke(any())).thenThrow(
                RuntimeException(
                    errorMessage
                )
            )

            nutritionLimitViewModel.event(NutritionLimitContract.Event.OnSave)

            assertThat(nutritionLimitViewModel.state.value.errorMessage?.messageRes).isNull()
            assertThat(nutritionLimitViewModel.state.value.errorMessage?.message).isEqualTo(
                errorMessage
            )
        }
    }

    @Nested
    inner class GetNutritionReferencesOnInstantiation {

        private val kcal = "12.3"
        private val protein = "34.5"
        private val carbohydrates = "567.8"
        private val fat = "11.2"

        @BeforeEach
        fun beforeEach() = runTest {
            clearInvocations(getNutritionReferencesUseCase)

            whenever(getNutritionReferencesUseCase.invoke()).thenReturn(
                flowOf(
                    NutritionLimitReferenceUiModel(
                        kcal = kcal,
                        protein = protein,
                        carbohydrates = carbohydrates,
                        fat = fat
                    )
                )
            )

            nutritionLimitViewModel = NutritionLimitViewModel(
                saveNutritionReferencesUseCase = saveNutritionReferencesUseCase,
                saveMilkQuantityReferencesUseCase = saveMilkQuantityReferencesUseCase,
                getNutritionReferencesUseCase = getNutritionReferencesUseCase,
                getMilkQuantityReferencesUseCase = getMilkQuantityReferencesUseCase
            )
        }

        @Test
        fun invokeUseCase() = runTest {
            verify(getNutritionReferencesUseCase).invoke()
        }

        @Test
        fun emitNutritionReferences() = runTest {
            assertThat(nutritionLimitViewModel.state.value.kcal.value).isEqualTo(kcal)
            assertThat(nutritionLimitViewModel.state.value.protein.value).isEqualTo(protein)
            assertThat(nutritionLimitViewModel.state.value.carbohydrates.value).isEqualTo(carbohydrates)
            assertThat(nutritionLimitViewModel.state.value.fat.value).isEqualTo(fat)
        }

        @Test
        fun emitErrorOnFailingUseCaseCall() = runTest {
            val errorMessage = "Something unexpected happened"

            whenever(getNutritionReferencesUseCase.invoke()).thenThrow(
                RuntimeException(errorMessage)
            )

            nutritionLimitViewModel = NutritionLimitViewModel(
                saveNutritionReferencesUseCase = saveNutritionReferencesUseCase,
                saveMilkQuantityReferencesUseCase = saveMilkQuantityReferencesUseCase,
                getNutritionReferencesUseCase = getNutritionReferencesUseCase,
                getMilkQuantityReferencesUseCase = getMilkQuantityReferencesUseCase
            )

            assertThat(nutritionLimitViewModel.state.value.errorMessage?.messageRes).isNull()
            assertThat(nutritionLimitViewModel.state.value.errorMessage?.message).isEqualTo(errorMessage)
        }
    }

    @Nested
    inner class GetMilkReferencesOnInstantiation {

        private val total = "400"
        private val day = "50"
        private val preNight = "50"
        private val night = "100"

        @BeforeEach
        fun beforeEach() = runTest {
            clearInvocations(getMilkQuantityReferencesUseCase)

            whenever(getMilkQuantityReferencesUseCase.invoke()).thenReturn(
                flowOf(
                    MilkLimitReferenceUiModel(
                        total = total,
                        day = day,
                        preNight = preNight,
                        night = night
                    )
                )
            )

            nutritionLimitViewModel = NutritionLimitViewModel(
                saveNutritionReferencesUseCase = saveNutritionReferencesUseCase,
                saveMilkQuantityReferencesUseCase = saveMilkQuantityReferencesUseCase,
                getNutritionReferencesUseCase = getNutritionReferencesUseCase,
                getMilkQuantityReferencesUseCase = getMilkQuantityReferencesUseCase
            )
        }

        @Test
        fun invokeUseCase() = runTest {
            verify(getMilkQuantityReferencesUseCase).invoke()
        }

        @Test
        fun emitNutritionReferences() = runTest {
            assertThat(nutritionLimitViewModel.state.value.totalQuantity.value).isEqualTo(total)
            assertThat(nutritionLimitViewModel.state.value.preNightQuantity.value).isEqualTo(preNight)
            assertThat(nutritionLimitViewModel.state.value.nightQuantity.value).isEqualTo(night)
        }

        @Test
        fun emitErrorOnFailingUseCaseCall() = runTest {
            val errorMessage = "Something unexpected happened"

            whenever(getMilkQuantityReferencesUseCase.invoke()).thenThrow(
                RuntimeException(errorMessage)
            )

            nutritionLimitViewModel = NutritionLimitViewModel(
                saveNutritionReferencesUseCase = saveNutritionReferencesUseCase,
                saveMilkQuantityReferencesUseCase = saveMilkQuantityReferencesUseCase,
                getNutritionReferencesUseCase = getNutritionReferencesUseCase,
                getMilkQuantityReferencesUseCase = getMilkQuantityReferencesUseCase
            )

            assertThat(nutritionLimitViewModel.state.value.errorMessage?.messageRes).isNull()
            assertThat(nutritionLimitViewModel.state.value.errorMessage?.message).isEqualTo(errorMessage)
        }
    }
}
