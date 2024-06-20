package com.example.thebotanyapp.domain.repository.database

import com.example.thebotanyapp.data.common.Resource
import com.example.thebotanyapp.domain.model.plant.Plant
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun getPlants(): Flow<Resource<List<Plant>>>
}