package com.example.thebotanyapp.domain.usecase.datastore

import com.example.thebotanyapp.domain.repository.datastore.DataStoreRepository
import javax.inject.Inject

class ClearSessionDataStoreUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    suspend operator fun invoke() =
        dataStoreRepository.clear()
}