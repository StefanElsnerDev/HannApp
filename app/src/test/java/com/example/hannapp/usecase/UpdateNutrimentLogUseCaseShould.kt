package com.example.hannapp.usecase

import com.example.hannapp.data.repository.NutrimentLogRepository
import com.example.hannapp.domain.UpdateNutrimentLogUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateNutrimentLogUseCaseShould {

    private lateinit var updateNutrimentLogUseCase: UpdateNutrimentLogUseCase
    private val nutrimentLogRepository = mock<NutrimentLogRepository>()

    private val logId = 6823L
    private val nutrimentId = 29941L
    private val quantity = 123.45

    @BeforeEach
    fun beforeEach() = runTest {
        updateNutrimentLogUseCase = UpdateNutrimentLogUseCase(
            nutrimentLogRepository = nutrimentLogRepository
        )
    }

    @Test
    fun invokeRepository() = runTest {
        updateNutrimentLogUseCase.update(
            logId = logId,
            nutrimentId = nutrimentId,
            quantity = quantity
        )

        verify(nutrimentLogRepository).update(
            logId = logId,
            nutrimentId = nutrimentId,
            quantity = quantity
        )
    }
}
