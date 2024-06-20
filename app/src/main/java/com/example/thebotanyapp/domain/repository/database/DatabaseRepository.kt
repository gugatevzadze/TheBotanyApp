package com.example.thebotanyapp.domain.repository.database

import com.example.thebotanyapp.domain.model.plant.Plant
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun getPlants(): Flow<List<Plant>>
}