package com.example.hannapp.viewmodel

import androidx.paging.PagingData
import com.example.hannapp.data.model.NutrimentUiLogModel
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.domain.DeleteNutrimentLogUseCase
import com.example.hannapp.domain.GetNutrimentLogUseCase
import com.example.hannapp.domain.GetNutritionUseCase
import com.example.hannapp.domain.GetPreNightMaltoSubstitutionUseCase
import com.example.hannapp.domain.GetPreNightMilkDiscardUseCase
import com.example.hannapp.domain.InsertNutrimentLogUseCase
import com.example.hannapp.domain.UpdateNutrimentLogUseCase
import com.example.hannapp.domain.ValidatePreNightNutritionLogUseCase
import com.example.hannapp.ui.mood.Mood
import com.example.hannapp.ui.viewmodel.NutrimentSelectContract
import com.example.hannapp.ui.viewmodel.NutritionSelectViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
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
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class NutritionSelectViewModelShould {

    private lateinit var nutritionViewModel: NutritionSelectViewModel
    private val getNutritionUseCase = mock(GetNutritionUseCase::class.java)
    private val insertNutrimentLogUseCase = mock(InsertNutrimentLogUseCase::class.java)
    private val updateNutrimentLogUseCase = mock(UpdateNutrimentLogUseCase::class.java)
    private val getNutrimentLogUseCase = mock(GetNutrimentLogUseCase::class.java)
    private val deleteNutrimentLogUseCase = mock(DeleteNutrimentLogUseCase::class.java)
    private val validatePreNightNutritionLogUseCase = mock(ValidatePreNightNutritionLogUseCase::class.java)
    private val getPreNightMilkDiscardUseCase = mock(GetPreNightMilkDiscardUseCase::class.java)
    private val getPreNightMaltoSubstitutionUseCase = mock(GetPreNightMaltoSubstitutionUseCase::class.java)

    private val testDispatcher = UnconfinedTestDispatcher()

    private val nutritionUiModels = listOf(
        NutritionUiModel(
            id = 100,
            name = "Apple",
            kcal = "1.2"
        ),
        NutritionUiModel(
            id = 200,
            name = "Banana",
            kcal = "3.4"
        )
    )

    private val pagingData = PagingData.from(nutritionUiModels)
    private val nutrimentsUiFlow = flowOf(pagingData)

    private val nutrimentUiLog = listOf(
        NutrimentUiLogModel(
            id = 1,
            nutrition = nutritionUiModels.first(),
            quantity = 1.1,
            unit = "",
            createdAt = 1681839531,
            modifiedAt = null
        ),
        NutrimentUiLogModel(
            id = 2,
            nutrition = nutritionUiModels.last(),
            quantity = 2.2,
            unit = "",
            createdAt = 1681234731,
            modifiedAt = null
        )
    )

    private val milkDiscard = 45.8
    private val maltoSubstitution = 33.3

    @BeforeEach
    fun beforeEach() {
        Dispatchers.setMain(testDispatcher)

        whenever(getNutritionUseCase.getAll()).thenReturn(
            nutrimentsUiFlow
        )

        whenever(getNutrimentLogUseCase()).thenReturn(
            flowOf(nutrimentUiLog)
        )

        whenever(validatePreNightNutritionLogUseCase.invoke()).thenReturn(
            flowOf(Mood.GREEN)
        )

        whenever(getPreNightMilkDiscardUseCase.invoke()).thenReturn(
            flowOf(milkDiscard)
        )

        whenever(getPreNightMaltoSubstitutionUseCase.invoke()).thenReturn(
            flowOf(maltoSubstitution)
        )

        nutritionViewModel = NutritionSelectViewModel(
            getNutritionUseCase = getNutritionUseCase,
            insertNutrimentLogUseCase = insertNutrimentLogUseCase,
            deleteNutrimentLogUseCase = deleteNutrimentLogUseCase,
            updateNutrimentLogUseCase = updateNutrimentLogUseCase,
            getNutrimentLogUseCase = getNutrimentLogUseCase,
            validatePreNightNutritionLogUseCase = validatePreNightNutritionLogUseCase,
            getPreNightMilkDiscardUseCase = getPreNightMilkDiscardUseCase,
            getPreNightMaltoSubstitutionUseCase = getPreNightMaltoSubstitutionUseCase,
            dispatcher = testDispatcher
        )
    }

    @AfterEach
    fun afterEach() {
        Dispatchers.resetMain()
    }

    @Test
    fun invokeGetNutritionUseCaseOn() = runTest {
        nutritionViewModel.event(NutrimentSelectContract.Event.OnGetAll)

        verify(getNutritionUseCase).getAll()
    }

    @Test
    fun produceUIStateWithLoadingStateOnInstantiation() = runTest {
        nutritionViewModel = NutritionSelectViewModel(
            getNutritionUseCase = getNutritionUseCase,
            insertNutrimentLogUseCase = insertNutrimentLogUseCase,
            deleteNutrimentLogUseCase = deleteNutrimentLogUseCase,
            updateNutrimentLogUseCase = updateNutrimentLogUseCase,
            getNutrimentLogUseCase = getNutrimentLogUseCase,
            validatePreNightNutritionLogUseCase = validatePreNightNutritionLogUseCase,
            getPreNightMilkDiscardUseCase = getPreNightMilkDiscardUseCase,
            getPreNightMaltoSubstitutionUseCase = getPreNightMaltoSubstitutionUseCase,
            dispatcher = testDispatcher
        )

        assertThat(nutritionViewModel.state.first())
            .isEqualTo(
                NutrimentSelectContract.State(
                    isLoading = true,
                    errorMessage = null,
                    log = nutrimentUiLog,
                    milkDiscard = milkDiscard.toString(),
                    maltoSubstitution = maltoSubstitution.toString()
                )
            )
    }

    @Test
    fun produceErrorUIStateOnException() = runTest {
        val errorMessage = "error"
        whenever(getNutritionUseCase.getAll()).thenReturn(
            flow {
                throw RuntimeException(errorMessage)
            }
        )

        nutritionViewModel.event(NutrimentSelectContract.Event.OnGetAll)

        assertThat(nutritionViewModel.state.first().errorMessage?.messageRes).isNotNull
        assertThat(nutritionViewModel.state.first().errorMessage?.message).isEqualTo(errorMessage)
    }

    @Nested
    inner class NutrimentLogFlow {

        @Test
        fun emitsNutrimentLog() = runTest {
            verify(getNutrimentLogUseCase).invoke()
            assertThat(nutritionViewModel.state.value.log).isEqualTo(nutrimentUiLog)
        }

        @Test
        fun emitsErrorStateOnFailingNutrimentLog() = runTest {
            val errorMessage = "Error"
            whenever(getNutrimentLogUseCase()).thenReturn(
                flow {
                    throw RuntimeException(errorMessage)
                }
            )

            nutritionViewModel = NutritionSelectViewModel(
                getNutritionUseCase = getNutritionUseCase,
                insertNutrimentLogUseCase = insertNutrimentLogUseCase,
                deleteNutrimentLogUseCase = deleteNutrimentLogUseCase,
                updateNutrimentLogUseCase = updateNutrimentLogUseCase,
                getNutrimentLogUseCase = getNutrimentLogUseCase,
                validatePreNightNutritionLogUseCase = validatePreNightNutritionLogUseCase,
                getPreNightMilkDiscardUseCase = getPreNightMilkDiscardUseCase,
                getPreNightMaltoSubstitutionUseCase = getPreNightMaltoSubstitutionUseCase,
                dispatcher = testDispatcher
            )

            assertThat(nutritionViewModel.state.value.errorMessage?.messageRes).isNull()
            assertThat(nutritionViewModel.state.value.errorMessage?.message).isEqualTo(errorMessage)
        }
    }

    @Nested
    inner class Select {

        @Test
        fun emitUiStateWithEmptyNutrimentUiModel() {
            assertThat(nutritionViewModel.state.value.nutritionUiModel).isEqualTo(
                NutritionUiModel()
            )
        }

        @Test
        fun emitUiStateWithErrorStateByDefault() {
            assertThat(nutritionViewModel.state.value.nutritionUiModel.id).isNull()
        }

        @Test
        fun emitUiStateWithSelectedNutrimentUiModel() {
            nutritionViewModel.event(NutrimentSelectContract.Event.OnSelect(nutritionUiModels.first()))

            assertThat(nutritionViewModel.state.value.nutritionUiModel).isEqualTo(
                nutritionUiModels.first()
            )
        }

        @Test
        fun emitUiStateWithValidationBasedOnModelId() {
            nutritionViewModel.event(NutrimentSelectContract.Event.OnSelect(nutritionUiModels.first()))

            assertThat(nutritionViewModel.state.value.nutritionUiModel.id).isNotNull
        }

        @Test
        fun emitErrorStateOnInvalidSelection() {
            nutritionViewModel.event(NutrimentSelectContract.Event.OnSelect(NutritionUiModel(id = null)))

            assertThat(nutritionViewModel.state.value.errorMessage?.messageRes).isNotNull
            assertThat(nutritionViewModel.state.value.errorMessage?.message).isNull()
        }
    }

    @Nested
    inner class SetQuantity {

        private val quantity = "123.45"

        @Test
        fun emitStateWithQuantity() {
            nutritionViewModel.event(NutrimentSelectContract.Event.OnSetQuantity(quantity = quantity))

            assertThat(nutritionViewModel.state.value.quantity).isEqualTo(quantity)
        }
    }

    @Nested
    inner class AddToLog {

        private val quantity = 56.78

        @BeforeEach
        fun beforeEach() = runTest {
            nutritionViewModel.event(NutrimentSelectContract.Event.OnSelect(nutritionUiModels.first()))

            nutritionViewModel.event(NutrimentSelectContract.Event.OnSetQuantity(quantity = quantity.toString()))
        }

        @Test
        fun callsUseCaseForAddingNutriment() = runTest {
            nutritionViewModel.event(NutrimentSelectContract.Event.OnAdd)

            verify(insertNutrimentLogUseCase).invoke(any(), any())
        }

        @Test
        fun emitsErrorStateOnFailingInsertion() = runTest {
            whenever(insertNutrimentLogUseCase.invoke(any(), any())).thenReturn(false)

            assertThat(nutritionViewModel.state.value.errorMessage).isNull()

            nutritionViewModel.event(NutrimentSelectContract.Event.OnAdd)

            assertThat(nutritionViewModel.state.value.errorMessage?.message).isNotBlank()
        }

        @Test
        fun emitsErrorStateOnThrowingInsertion() = runTest {
            val errorMessage = "Logging failed!"
            whenever(insertNutrimentLogUseCase.invoke(any(), any())).thenThrow(
                RuntimeException(
                    errorMessage
                )
            )

            nutritionViewModel.event(NutrimentSelectContract.Event.OnAdd)

            assertThat(nutritionViewModel.state.value.errorMessage?.message).isEqualTo(errorMessage)
        }

        @Test
        fun clearsQuantityOnSuccessfulLog() {
            nutritionViewModel.event(NutrimentSelectContract.Event.OnAdd)

            assertThat(nutritionViewModel.state.value.quantity).isEmpty()
        }
    }

    @Nested
    inner class ClearAll {

        @Test
        fun invokeDeleteLogUseCase() = runTest {
            nutritionViewModel.event(NutrimentSelectContract.Event.OnClearAll)

            verify(deleteNutrimentLogUseCase).clear()
        }

        @Test
        fun emitErrorStateOnFailingDeletion() = runTest {
            whenever(deleteNutrimentLogUseCase.clear()).thenReturn(false)

            nutritionViewModel.event(NutrimentSelectContract.Event.OnClearAll)

            assertThat(nutritionViewModel.state.value.errorMessage?.messageRes).isNotNull
            assertThat(nutritionViewModel.state.value.errorMessage?.message).isNull()
        }

        @Test
        fun emitErrorStateWithExceptionMessageOnFailedDeletion() = runTest {
            val errorMessage = "Any strange error"
            whenever(deleteNutrimentLogUseCase.clear()).thenThrow(RuntimeException(errorMessage))

            nutritionViewModel.event(NutrimentSelectContract.Event.OnClearAll)

            assertThat(nutritionViewModel.state.value.errorMessage?.messageRes).isNotNull
            assertThat(nutritionViewModel.state.value.errorMessage?.message).isEqualTo(errorMessage)
        }

        @Test
        fun emitErrorStateOnUnexpectedErrorDuringDeletion() = runTest {
            whenever(deleteNutrimentLogUseCase.clear()).thenThrow(RuntimeException())

            nutritionViewModel.event(NutrimentSelectContract.Event.OnClearAll)

            assertThat(nutritionViewModel.state.value.errorMessage?.messageRes).isNotNull
            assertThat(nutritionViewModel.state.value.errorMessage?.message).isNull()
        }

        @Test
        fun emitStateWithEmptyModelState() {
            nutritionViewModel.event(NutrimentSelectContract.Event.OnClearAll)

            assertThat(nutritionViewModel.state.value.nutritionUiModel.id).isNull()
        }
    }

    @Nested
    inner class EditLoggedNutriment {

        private val nutrimentId = 123L
        private val logId = 987L
        private val quantity = 123.45

        private val substitutedNutrimentId = 1234567L
        private val substitutedQuantity = 54.321

        private val nutritionUiModel = NutritionUiModel(
            id = nutrimentId,
            name = "Milk"
        )

        private val nutrimentUiLogModel = NutrimentUiLogModel(
            id = logId,
            nutrition = nutritionUiModel,
            quantity = quantity,
            unit = "ml",
            createdAt = 123456789,
            modifiedAt = null
        )

        private val substitutedNutriment = NutritionUiModel(
            id = substitutedNutrimentId,
            name = "Pepsi Cola Sugar free"
        )

        @Nested
        inner class SelectLoggedNutriment {

            @Test
            fun emitLogNutrimentAndQuantity() {
                nutritionViewModel.event(NutrimentSelectContract.Event.OnEdit(nutrimentUiLogModel))

                assertThat(nutritionViewModel.state.value.nutritionUiModel).isEqualTo(
                    nutritionUiModel
                )
                assertThat(nutritionViewModel.state.value.quantity).isEqualTo(quantity.toString())
            }
        }

        @Nested
        inner class UpdateLoggedNutriment {

            @BeforeEach
            fun beforeEach() {
                nutritionViewModel.event(NutrimentSelectContract.Event.OnEdit(nutrimentUiLogModel))

                nutritionViewModel.event(NutrimentSelectContract.Event.OnSelect(substitutedNutriment))
                nutritionViewModel.event(
                    NutrimentSelectContract.Event.OnSetQuantity(
                        substitutedQuantity.toString()
                    )
                )
            }

            @Test
            fun invokeDaoOnSelectedLogNutriment() = runTest {
                nutritionViewModel.event(NutrimentSelectContract.Event.OnUpdate)

                verify(updateNutrimentLogUseCase).update(any(), any(), any())
            }

            @Test
            fun saveChangedNutrimentAndModifiedQuantity() = runTest {
                nutritionViewModel.event(NutrimentSelectContract.Event.OnUpdate)

                verify(updateNutrimentLogUseCase).update(
                    logId = logId,
                    nutrimentId = substitutedNutrimentId,
                    quantity = substitutedQuantity
                )
            }
        }

        @Nested
        inner class AbortUpdate {

            @BeforeEach
            fun beforeEach() {
                nutritionViewModel.event(NutrimentSelectContract.Event.OnSelect(nutritionUiModel = nutritionUiModel))
                nutritionViewModel.event(NutrimentSelectContract.Event.OnSetQuantity(quantity.toString()))

                nutritionViewModel.event(NutrimentSelectContract.Event.OnEdit(nutrimentUiLogModel))
            }

            @Test
            fun restoreLogNutriment() {
                nutritionViewModel.event(NutrimentSelectContract.Event.OnSelect(substitutedNutriment))

                assertThat(nutritionViewModel.state.value.nutritionUiModel).isEqualTo(
                    substitutedNutriment
                )

                nutritionViewModel.event(NutrimentSelectContract.Event.OnAbort)

                assertThat(nutritionViewModel.state.value.nutritionUiModel).isEqualTo(
                    nutritionUiModel
                )
            }

            @Test
            fun restoreQuantity() {
                nutritionViewModel.event(
                    NutrimentSelectContract.Event.OnSetQuantity(
                        substitutedQuantity.toString()
                    )
                )

                assertThat(nutritionViewModel.state.value.quantity).isEqualTo(substitutedQuantity.toString())

                nutritionViewModel.event(NutrimentSelectContract.Event.OnAbort)

                assertThat(nutritionViewModel.state.value.quantity).isEqualTo(quantity.toString())
            }
        }
    }

    @Nested
    inner class EmitMood {

        @Test
        fun invokeValidationUseCaseOnInstantiation() = runTest {
            verify(validatePreNightNutritionLogUseCase).invoke()
            assertThat(nutritionViewModel.state.value.validation).isEqualTo(Mood.GREEN)
        }

        @Test
        fun emitValidation() = runTest {
            whenever(validatePreNightNutritionLogUseCase.invoke()).thenReturn(
                flowOf(Mood.RED)
            )

            nutritionViewModel.event(NutrimentSelectContract.Event.OnValidate)

            assertThat(nutritionViewModel.state.value.validation).isEqualTo(Mood.RED)
        }

        @Test
        fun emitErrorOnValidationError() = runTest {
            val errorMessage = "Failed requirement"

            whenever(validatePreNightNutritionLogUseCase.invoke()).thenThrow(
                IllegalArgumentException(errorMessage)
            )

            assertThat(nutritionViewModel.state.value.errorMessage?.message).isNull()

            nutritionViewModel.event(NutrimentSelectContract.Event.OnValidate)

            assertThat(nutritionViewModel.state.value.errorMessage?.message).isNotNull.isEqualTo(errorMessage)
        }

        @Test
        fun emitErrorOnUnexpectedError() = runTest {
            val errorMessage = "Unexpected Error"

            whenever(validatePreNightNutritionLogUseCase.invoke()).thenThrow(
                RuntimeException(errorMessage)
            )

            nutritionViewModel.event(NutrimentSelectContract.Event.OnValidate)

            assertThat(nutritionViewModel.state.value.errorMessage?.message).isNotNull.isEqualTo(errorMessage)
        }
    }

    @Nested
    inner class EmitMilkDiscard {
        @Test
        fun invokeUseCase() {
            verify(getPreNightMilkDiscardUseCase).invoke()
        }

        @Test
        fun emitMilkDiscard() {
            assertThat(nutritionViewModel.state.value.milkDiscard).isEqualTo(milkDiscard.toString())
        }
    }

    @Nested
    inner class EmitMaltSubstitution {
        @Test
        fun invokeUseCase() {
            verify(getPreNightMaltoSubstitutionUseCase).invoke()
        }

        @Test
        fun emitMilkDiscard() {
            assertThat(nutritionViewModel.state.value.maltoSubstitution).isEqualTo(maltoSubstitution.toString())
        }
    }
}
