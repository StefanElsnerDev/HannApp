package com.example.hannapp.viewmodel

import androidx.paging.PagingData
import com.example.hannapp.data.model.NutrimentUiLogModel
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.domain.DeleteNutrimentLogUseCase
import com.example.hannapp.domain.GetNutrimentLogUseCase
import com.example.hannapp.domain.GetNutritionUseCase
import com.example.hannapp.domain.InsertNutrimentLogUseCase
import com.example.hannapp.domain.UpdateNutrimentLogUseCase
import com.example.hannapp.ui.viewmodel.NutrimentSelectUiState
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
import org.junit.jupiter.api.Assertions
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

    @BeforeEach
    fun beforeEach() {
        Dispatchers.setMain(testDispatcher)

        whenever(getNutritionUseCase.getAll()).thenReturn(
            nutrimentsUiFlow
        )

        whenever(getNutrimentLogUseCase.observeNutrimentLog()).thenReturn(
            flowOf(nutrimentUiLog)
        )

        nutritionViewModel = NutritionSelectViewModel(
            getNutritionUseCase = getNutritionUseCase,
            insertNutrimentLogUseCase = insertNutrimentLogUseCase,
            deleteNutrimentLogUseCase = deleteNutrimentLogUseCase,
            updateNutrimentLogUseCase = updateNutrimentLogUseCase,
            getNutrimentLogUseCase = getNutrimentLogUseCase,
            dispatcher = testDispatcher
        )
    }

    @AfterEach
    fun afterEach() {
        Dispatchers.resetMain()
    }

    @Test
    fun invokeGetNutritionUseCaseOn() = runTest {
        nutritionViewModel.getAll()

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
            dispatcher = testDispatcher
        )

        Assertions.assertEquals(
            NutrimentSelectUiState.LogUiState(
                isLoading = true,
                errorMessage = null
            ),
            nutritionViewModel.uiState.first()
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

        nutritionViewModel.getAll()

        assertThat(nutritionViewModel.uiState.first().errorMessage?.messageRes).isNotNull
        assertThat(nutritionViewModel.uiState.first().errorMessage?.message).isEqualTo(errorMessage)
    }

    @Nested
    inner class NutrimentLogFlow {

        @Test
        fun emitsNutrimentLog() = runTest {
            assertThat(nutritionViewModel.nutrimentLog.first()).isEqualTo(nutrimentUiLog)
        }

        @Test
        fun emitsErrorStateOnFailingNutrimentLog() = runTest {
            val errorMessage = "Error"
            whenever(getNutrimentLogUseCase.observeNutrimentLog()).thenReturn(
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
                dispatcher = testDispatcher
            )

            assertThat(nutritionViewModel.uiState.value.errorMessage?.messageRes).isNull()
            assertThat(nutritionViewModel.uiState.value.errorMessage?.message).isEqualTo(errorMessage)
        }
    }

    @Nested
    inner class Select {

        @Test
        fun emitUiStateWithEmptyNutrimentUiModel() {
            assertThat(nutritionViewModel.uiState.value.nutritionUiModel).isEqualTo(
                NutritionUiModel()
            )
        }

        @Test
        fun emitUiStateWithErrorStateByDefault() {
            assertThat(nutritionViewModel.uiState.value.nutritionUiModel.id).isNull()
        }

        @Test
        fun emitUiStateWithSelectedNutrimentUiModel() {
            nutritionViewModel.select(nutritionUiModels.first())

            assertThat(nutritionViewModel.uiState.value.nutritionUiModel).isEqualTo(
                nutritionUiModels.first()
            )
        }

        @Test
        fun emitUiStateWithValidationBasedOnModelId() {
            nutritionViewModel.select(nutritionUiModels.first())

            assertThat(nutritionViewModel.uiState.value.nutritionUiModel.id).isNotNull()
        }

        @Test
        fun emitErrorStateOnInvalidSelection() {
            nutritionViewModel.select(NutritionUiModel(id = null))

            assertThat(nutritionViewModel.uiState.value.errorMessage?.messageRes).isNotNull
            assertThat(nutritionViewModel.uiState.value.errorMessage?.message).isNull()
        }
    }

    @Nested
    inner class SetQuantity {

        private val quantity = "123.45"

        @Test
        fun emitStateWithQuantity() {
            nutritionViewModel.setQuantity(quantity = quantity)

            assertThat(nutritionViewModel.uiState.value.quantity).isEqualTo(quantity)
        }
    }

    @Nested
    inner class AddToLog {

        private val quantity = 56.78

        @BeforeEach
        fun beforeEach() = runTest {
            nutritionViewModel.select(nutritionUiModels.first())

            nutritionViewModel.setQuantity(quantity = quantity.toString())
        }

        @Test
        fun callsUseCaseForAddingNutriment() = runTest {
            nutritionViewModel.add()

            verify(insertNutrimentLogUseCase).invoke(any(), any())
        }

        @Test
        fun emitsErrorStateOnFailingInsertion() = runTest {
            whenever(insertNutrimentLogUseCase.invoke(any(), any())).thenReturn(false)

            assertThat(nutritionViewModel.uiState.value.errorMessage).isNull()

            nutritionViewModel.add()

            assertThat(nutritionViewModel.uiState.value.errorMessage?.message).isNotBlank()
        }

        @Test
        fun emitsErrorStateOnThrowingInsertion() = runTest {
            val errorMessage = "Logging failed!"
            whenever(insertNutrimentLogUseCase.invoke(any(), any())).thenThrow(
                RuntimeException(
                    errorMessage
                )
            )

            nutritionViewModel.add()

            assertThat(nutritionViewModel.uiState.value.errorMessage?.message).isEqualTo(errorMessage)
        }

        @Test
        fun clearsQuantityOnSuccesfulLog() {
            nutritionViewModel.add()

            assertThat(nutritionViewModel.uiState.value.quantity).isEmpty()
        }
    }

    @Nested
    inner class ClearAll {

        @Test
        fun invokeDeleteLogUseCase() = runTest {
            nutritionViewModel.clearAll()

            verify(deleteNutrimentLogUseCase).clear()
        }

        @Test
        fun emitErrorStateOnFailingDeletion() = runTest {
            whenever(deleteNutrimentLogUseCase.clear()).thenReturn(false)

            nutritionViewModel.clearAll()

            assertThat(nutritionViewModel.uiState.value.errorMessage?.messageRes).isNotNull
            assertThat(nutritionViewModel.uiState.value.errorMessage?.message).isNull()
        }

        @Test
        fun emitErrorStateWithExceptionMessageOnFailedDeletion() = runTest {
            val errorMessage = "Any strange error"
            whenever(deleteNutrimentLogUseCase.clear()).thenThrow(RuntimeException(errorMessage))

            nutritionViewModel.clearAll()

            assertThat(nutritionViewModel.uiState.value.errorMessage?.messageRes).isNotNull
            assertThat(nutritionViewModel.uiState.value.errorMessage?.message).isEqualTo(errorMessage)
        }

        @Test
        fun emitErrorStateOnUnexpectedErrorDuringDeletion() = runTest {
            whenever(deleteNutrimentLogUseCase.clear()).thenThrow(RuntimeException())

            nutritionViewModel.clearAll()

            assertThat(nutritionViewModel.uiState.value.errorMessage?.messageRes).isNotNull
            assertThat(nutritionViewModel.uiState.value.errorMessage?.message).isNull()
        }

        @Test
        fun emitStateWithEmptyModelState() {
            nutritionViewModel.clearAll()

            assertThat(nutritionViewModel.uiState.value.nutritionUiModel.id).isNull()
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
                nutritionViewModel.edit(nutrimentUiLogModel)

                assertThat(nutritionViewModel.uiState.value.nutritionUiModel).isEqualTo(
                    nutritionUiModel
                )
                assertThat(nutritionViewModel.uiState.value.quantity).isEqualTo(quantity.toString())
            }
        }

        @Nested
        inner class UpdateLoggedNutriment {

            @BeforeEach
            fun beforeEach() {
                nutritionViewModel.edit(nutrimentUiLogModel)

                nutritionViewModel.select(substitutedNutriment)
                nutritionViewModel.setQuantity(substitutedQuantity.toString())
            }

            @Test
            fun invokeDaoOnSelectedLogNutriment() = runTest {
                nutritionViewModel.update()

                verify(updateNutrimentLogUseCase).update(any(), any(), any())
            }

            @Test
            fun saveChangedNutrimentAndModifiedQuantity() = runTest {
                nutritionViewModel.update()

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
                nutritionViewModel.select(nutritionUiModel = nutritionUiModel)
                nutritionViewModel.setQuantity(quantity.toString())

                nutritionViewModel.edit(nutrimentUiLogModel)
            }

            @Test
            fun restoreLogNutriment() {
                nutritionViewModel.select(substitutedNutriment)

                assertThat(nutritionViewModel.uiState.value.nutritionUiModel).isEqualTo(substitutedNutriment)

                nutritionViewModel.abort()

                assertThat(nutritionViewModel.uiState.value.nutritionUiModel).isEqualTo(nutritionUiModel)
            }

            @Test
            fun restoreQuantity() {
                nutritionViewModel.setQuantity(substitutedQuantity.toString())

                assertThat(nutritionViewModel.uiState.value.quantity).isEqualTo(substitutedQuantity.toString())

                nutritionViewModel.abort()

                assertThat(nutritionViewModel.uiState.value.quantity).isEqualTo(quantity.toString())
            }
        }
    }
}
