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
    suspend fun save(milkReferenceModel: MilkReferenceModel) = dataStore.edit { preferences ->
        milkReferenceModel.apply {
            maxQuantity?.let { preferences[floatPreferencesKey(QUANTITY_MAX)] }
            dayTimeQuantity?.let { preferences[floatPreferencesKey(DAYTIME_QUANTITY)] }
            preNightQuantity?.let { preferences[floatPreferencesKey(PRE_NIGHT_QUANTITY)] }
            nightQuantity?.let { preferences[floatPreferencesKey(NIGHT_QUANTITY)] }
        }
    }.asMap().size

    fun emitReferences(): Flow<MilkReferenceModel> = dataStore.data.map { preferences ->
        MilkReferenceModel(
            maxQuantity = preferences[floatPreferencesKey(QUANTITY_MAX)],
            dayTimeQuantity = preferences[floatPreferencesKey(DAYTIME_QUANTITY)],
            preNightQuantity = preferences[floatPreferencesKey(PRE_NIGHT_QUANTITY)],
            nightQuantity = preferences[floatPreferencesKey(NIGHT_QUANTITY)]
        )
    }
}
