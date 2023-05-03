package com.example.hannapp.repository

import com.example.hannapp.data.database.dao.NutrimentLogDao
import com.example.hannapp.data.repository.NutrimentLogRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class NutrimentLogRepositoryShould {

    private lateinit var nutrimentLogRepository: NutrimentLogRepository
    private val nutrimentLogDao = mock<NutrimentLogDao>()

    @BeforeEach
    fun beforeEach() = runTest {
        whenever(nutrimentLogDao.deleteAll()).thenReturn(3)

        nutrimentLogRepository = NutrimentLogRepository(
            nutrimentLogDao = nutrimentLogDao
        )
    }

    @Nested
    inner class ClearLog {

        @Test
        fun invokeUseCase() = runTest {
            nutrimentLogRepository.clearLog()

            verify(nutrimentLogDao).deleteAll()
        }

        @Test
        fun returnTrueForSuccessfulDeletion() = runTest {
            val result = nutrimentLogRepository.clearLog()

            assertThat(result).isTrue()
        }

        @ParameterizedTest
        @ValueSource(ints = [-1, 0])
        fun returnFalseForFailedDeletion(deletedRows: Int) = runTest {
            whenever(nutrimentLogDao.deleteAll()).thenReturn(deletedRows)

            val result = nutrimentLogRepository.clearLog()

            assertThat(result).isFalse()
        }
    }
}
