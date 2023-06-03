package com.example.hannapp.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.hannapp.Constants.CARBOHYDRATES_MAX
import com.example.hannapp.Constants.FAT_MAX
import com.example.hannapp.Constants.KCAL_MAX
import com.example.hannapp.Constants.PROTEIN_MAX
import com.example.hannapp.data.model.NutritionReferences
import com.example.hannapp.data.modul.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NutritionReferenceDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun save(nutritionReferences: NutritionReferences) = dataStore.edit { preferences ->
        nutritionReferences.apply {
            kcal?.let { preferences[doublePreferencesKey(KCAL_MAX)] = it }
            protein?.let { preferences[doublePreferencesKey(PROTEIN_MAX)] = it }
            carbohydrates?.let { preferences[doublePreferencesKey(CARBOHYDRATES_MAX)] = it }
            fat?.let { preferences[doublePreferencesKey(FAT_MAX)] = it }
        }
    }

    fun emitReferences(): Flow<NutritionReferences> = dataStore.data.map { preferences ->
        NutritionReferences(
            kcal = preferences[doublePreferencesKey(KCAL_MAX)],
            protein = preferences[doublePreferencesKey(PROTEIN_MAX)],
            carbohydrates = preferences[doublePreferencesKey(CARBOHYDRATES_MAX)],
            fat = preferences[doublePreferencesKey(FAT_MAX)]
        )
    }.flowOn(dispatcher)
}
