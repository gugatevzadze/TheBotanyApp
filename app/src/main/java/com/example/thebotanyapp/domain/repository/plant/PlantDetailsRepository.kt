package com.example.thebotanyapp.domain.repository.plant

import com.example.thebotanyapp.data.common.Resource
import com.example.thebotanyapp.domain.model.plant.Plant
import kotlinx.coroutines.flow.Flow

//interface PlantDetailsRepository {
//    suspend fun getPlantDetails(id: Int): Flow<Resource<Plant>>
//}

interface PlantDetailsRepository {
    suspend fun getPlantDetails(id: Int): Flow<Resource<Plant>>
}