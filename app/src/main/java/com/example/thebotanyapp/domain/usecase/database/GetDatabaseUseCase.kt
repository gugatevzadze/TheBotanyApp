package com.example.thebotanyapp.domain.usecase.database

import androidx.lifecycle.LiveData
import com.example.thebotanyapp.data.common.Resource
import com.example.thebotanyapp.domain.model.plant.Plant
import com.example.thebotanyapp.domain.repository.database.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDatabaseUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Plant>>> {
        return databaseRepository.getPlants()
    }
}