package com.example.thebotanyapp.domain.usecase.datastore


import com.example.thebotanyapp.domain.repository.datastore.DataStoreRepository
import com.example.thebotanyapp.domain.user_data_key.SessionKey
import javax.inject.Inject


class ReadSessionDataStoreUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {

    operator fun invoke() = dataStoreRepository.readSession(key = SessionKey.TOKEN)
}
