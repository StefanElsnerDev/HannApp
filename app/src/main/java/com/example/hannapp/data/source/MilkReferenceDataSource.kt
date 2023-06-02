package com.example.hannapp.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import com.example.hannapp.Constants.DAYTIME_QUANTITY
import com.example.hannapp.Constants.NIGHT_QUANTITY
import com.example.hannapp.Constants.PRE_NIGHT_QUANTITY
import com.example.hannapp.Constants.QUANTITY_MAX
import com.example.hannapp.data.model.MilkReferenceModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MilkReferenceDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun save(milkReferenceModel: MilkReferenceModel) =
        dataStore.edit { preferences ->
            milkReferenceModel.apply {
                preferences[floatPreferencesKey(QUANTITY_MAX)] = maxQuantity
                preferences[floatPreferencesKey(DAYTIME_QUANTITY)] = dayTimeQuantity
                preferences[floatPreferencesKey(PRE_NIGHT_QUANTITY)] = preNightQuantity
                preferences[floatPreferencesKey(NIGHT_QUANTITY)] = nightQuantity
            }
        }

    fun emitReferences(): Flow<MilkReferenceModel> = dataStore.data.map { preferences ->
        val quantityMax = preferences[floatPreferencesKey(QUANTITY_MAX)]
        val dayTimeQuantity = preferences[floatPreferencesKey(DAYTIME_QUANTITY)]
        val preNightQuantity = preferences[floatPreferencesKey(PRE_NIGHT_QUANTITY)]
        val nightQuantity = preferences[floatPreferencesKey(NIGHT_QUANTITY)]

        require(quantityMax != null)
        require(dayTimeQuantity != null)
        require(preNightQuantity != null)
        require(nightQuantity != null)

        MilkReferenceModel(
            maxQuantity = quantityMax,
            dayTimeQuantity = dayTimeQuantity,
            preNightQuantity = preNightQuantity,
            nightQuantity = nightQuantity
        )
    }
}
