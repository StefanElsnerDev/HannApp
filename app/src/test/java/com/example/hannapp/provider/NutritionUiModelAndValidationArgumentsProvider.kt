package com.example.hannapp.provider

import com.example.hannapp.data.model.NutritionUiModel
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.stream.Stream

class NutritionUiModelAndValidationArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> =
        Stream.of(
            Arguments.of(NutritionUiModel(id = 123), true),
            Arguments.of(NutritionUiModel(id = null), false)
        )
}