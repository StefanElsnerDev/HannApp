package com.example.hannapp.usecase

import com.example.hannapp.data.model.MilkLimitReferenceUiModel
import com.example.hannapp.data.model.MilkReferenceUiModel
import com.example.hannapp.data.repository.MilkReferenceRepository
import com.example.hannapp.domain.SaveMilkQuantityReferencesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.util.stream.Stream

@OptIn(ExperimentalCoroutinesApi::class)
class SaveMilkQuantityReferencesUseCaseShould {

    private lateinit var saveMilkQuantityReferencesUseCase: SaveMilkQuantityReferencesUseCase
    private val milkReferenceRepository = mock<MilkReferenceRepository>()
    private val dispatcher = UnconfinedTestDispatcher()

    private val milkLimitReferenceUiModel = MilkLimitReferenceUiModel(
        total = "400",
        day = "200",
        preNight = "100",
        night = "100"
    )

    private val milkReferenceUiModel = MilkReferenceUiModel(
        maxQuantity = 400f,
        dayTimeQuantity = 200f,
        preNightQuantity = 100f,
        nightQuantity = 100f
    )

    @BeforeEach
    fun beforeEach() {
        saveMilkQuantityReferencesUseCase = SaveMilkQuantityReferencesUseCase(
            milkReferenceRepository = milkReferenceRepository,
            dispatcher = dispatcher
        )
    }

    @Test
    fun invokeRepository() = runTest {
        saveMilkQuantityReferencesUseCase.invoke(milkLimitReferenceUiModel)

        verify(milkReferenceRepository).save(any())
    }

    @Test
    fun transformModelOnCallingRepo() = runTest {
        saveMilkQuantityReferencesUseCase.invoke(milkLimitReferenceUiModel)

        verify(milkReferenceRepository).save(milkReferenceUiModel)
    }

    @ParameterizedTest
    @MethodSource("provideFloatsForCalculation")
    fun calculateDayPropertyWhenNullOnCallingRepo(total: Float, night: Float, day: Float) =
        runTest {
            val modelWithoutDayQuantity = MilkLimitReferenceUiModel(
                total = total.toString(),
                day = null,
                preNight = "100",
                night = night.toString()
            )

            val expectedModelWithCalculatedDay = MilkReferenceUiModel(
                maxQuantity = total,
                dayTimeQuantity = day,
                preNightQuantity = 100f,
                nightQuantity = night
            )

            saveMilkQuantityReferencesUseCase.invoke(modelWithoutDayQuantity)

            verify(milkReferenceRepository).save(expectedModelWithCalculatedDay)
        }

    companion object {
        @JvmStatic
        fun provideFloatsForCalculation(): Stream<Arguments>? {
            return Stream.of(
                Arguments.of(100f, 100f, 0f),
                Arguments.of(200f, 100f, 100f),
                Arguments.of(1060f, 340f, 720f)
            )
        }
    }
}
