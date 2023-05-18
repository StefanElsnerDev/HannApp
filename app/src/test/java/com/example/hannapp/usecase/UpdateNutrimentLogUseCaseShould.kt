package com.example.hannapp.usecase

import com.example.hannapp.data.model.NutrimentUiLogModel
import com.example.hannapp.data.model.NutritionUiModel
import com.example.hannapp.data.repository.NutrimentLogRepository
import com.example.hannapp.domain.UpdateNutrimentLogUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateNutrimentLogUseCaseShould {

    private lateinit var updateNutrimentLogUseCase: UpdateNutrimentLogUseCase
    private val nutrimentLogRepository = mock<NutrimentLogRepository>()

    private val nutrimentUiLogModel = NutrimentUiLogModel(
        id = 123,
        nutrition = NutritionUiModel(
            id = 1),
        quantity = 123.45,
        unit = "ml",
        timeStamp = 1684431644
    )

    @BeforeEach
    fun beforeEach() = runTest {
        updateNutrimentLogUseCase = UpdateNutrimentLogUseCase(
            nutrimentLogRepository = nutrimentLogRepository
        )
    }

    @Test
    fun invokeRepository() = runTest {
        updateNutrimentLogUseCase.update(nutrimentUiLogModel)

        verify(nutrimentLogRepository).update(eq(nutrimentUiLogModel), any())
    }
}
