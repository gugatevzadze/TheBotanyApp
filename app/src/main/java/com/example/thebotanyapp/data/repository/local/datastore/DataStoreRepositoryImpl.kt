package com.example.thebotanyapp.data.repository.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.thebotanyapp.domain.repository.datastore.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl@Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {
    override suspend fun saveSession(key: Preferences.Key<Boolean>, value: Boolean) {
        dataStore.edit { settings ->
            settings[key] = value
        }
    }

    override fun readSession(key: Preferences.Key<Boolean>): Flow<Boolean> {
        return dataStore.data
            .map { preferences ->
                preferences[key] ?: false
            }
    }

    override suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }
}