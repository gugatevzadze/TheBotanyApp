package com.example.thebotanyapp.domain.usecase.datastore

import com.example.thebotanyapp.domain.repository.datastore.DataStoreRepository
import com.example.thebotanyapp.domain.user_data_key.SessionKey
import javax.inject.Inject


class SaveSessionDataStoreUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    suspend operator fun invoke(value: Boolean) {
        dataStoreRepository.saveSession(key = SessionKey.TOKEN, value = value)
    }
}

