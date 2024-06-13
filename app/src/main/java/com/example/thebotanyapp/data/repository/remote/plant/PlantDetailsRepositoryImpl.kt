package com.example.thebotanyapp.data.repository.remote.plant

import com.example.thebotanyapp.data.common.ApiResponseHandler
import com.example.thebotanyapp.data.common.Resource
import com.example.thebotanyapp.data.remote.mapper.base.mapToDomain
import com.example.thebotanyapp.data.remote.mapper.plant.toDomain
import com.example.thebotanyapp.data.remote.service.plant.PlantDetailsApiService
import com.example.thebotanyapp.domain.model.plant.Plant
import com.example.thebotanyapp.domain.repository.plant.PlantDetailsRepository
import kotlinx.coroutines.flow.Flow

class PlantDetailsRepositoryImpl(
    private val plantDetailsApiService: PlantDetailsApiService,
    private val responseHandler: ApiResponseHandler
): PlantDetailsRepository {
    override suspend fun getPlantDetails(id: Int): Flow<Resource<Plant>> {
        return responseHandler.safeApiCall {
            plantDetailsApiService.getPlantDetails(id)
        }.mapToDomain { plantDetailDto ->
            plantDetailDto.toDomain()
        }
    }
}

//class PlantDetailsRepositoryImpl(
//    private val plantDetailsApiService: PlantDetailsApiService,
//    private val responseHandler: ApiResponseHandler
//): PlantDetailsRepository {
//    override suspend fun getPlantDetails(id: Int): Flow<Resource<Plant>> {
//        return responseHandler.safeApiCall {
//            plantDetailsApiService.getPlantDetails()
//        }.mapToDomain { plantDetailDto ->
//            plantDetailDto.toDomain()
//        }
//    }
//}