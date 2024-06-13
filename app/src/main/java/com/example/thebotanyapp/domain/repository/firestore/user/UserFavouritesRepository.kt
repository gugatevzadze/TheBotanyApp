package com.example.thebotanyapp.domain.repository.firestore.user

import com.example.thebotanyapp.data.common.Resource
import com.example.thebotanyapp.domain.model.plant.Plant
import kotlinx.coroutines.flow.Flow

interface UserFavouritesRepository {
    suspend fun addFavouritePlant(plant: Plant): Flow<Resource<Boolean>>

    suspend fun removeFavouritePlant(plant: Plant): Flow<Resource<Boolean>>

    suspend fun retrieveFavouritePlants(): Flow<Resource<List<Plant>>>
}