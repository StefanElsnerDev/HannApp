package com.example.hannapp.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.hannapp.Constants.CARBOHYDRATES_MAX
import com.example.hannapp.Constants.FAT_MAX
import com.example.hannapp.Constants.KCAL_MAX
import com.example.hannapp.Constants.PROTEIN_MAX
import com.example.hannapp.data.model.NutritionReferenceModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NutritionReferenceDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun save(nutritionReferenceModel: NutritionReferenceModel) =
        dataStore.edit { preferences ->
            nutritionReferenceModel.apply {
                preferences[doublePreferencesKey(KCAL_MAX)] = kcal
                preferences[doublePreferencesKey(PROTEIN_MAX)] = protein
                preferences[doublePreferencesKey(CARBOHYDRATES_MAX)] = carbohydrates
                preferences[doublePreferencesKey(FAT_MAX)] = fat
            }
        }

    fun emitReferences(): Flow<NutritionReferenceModel> = dataStore.data.map { preferences ->
        val kcal = preferences[doublePreferencesKey(KCAL_MAX)]
        val protein = preferences[doublePreferencesKey(PROTEIN_MAX)]
        val carbohydrates = preferences[doublePreferencesKey(CARBOHYDRATES_MAX)]
        val fat = preferences[doublePreferencesKey(FAT_MAX)]

        require(kcal != null)
        require(protein != null)
        require(carbohydrates != null)
        require(fat != null)

        NutritionReferenceModel(
            kcal = kcal,
            protein = protein,
            carbohydrates = carbohydrates,
            fat = fat
        )
    }
}
