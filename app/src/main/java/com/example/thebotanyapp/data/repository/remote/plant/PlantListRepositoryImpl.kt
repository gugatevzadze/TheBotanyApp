package com.example.thebotanyapp.data.repository.remote.plant

import com.example.thebotanyapp.data.common.ApiResponseHandler
import com.example.thebotanyapp.data.common.Resource
import com.example.thebotanyapp.data.remote.mapper.base.mapToDomain
import com.example.thebotanyapp.data.remote.mapper.plant.toDomain
import com.example.thebotanyapp.data.remote.service.plant.PlantListApiService
import com.example.thebotanyapp.domain.model.plant.Plant
import com.example.thebotanyapp.domain.repository.plant.PlantListRepository
import kotlinx.coroutines.flow.Flow

class PlantListRepositoryImpl(
    private val plantListApiService: PlantListApiService,
    private val responseHandler: ApiResponseHandler
): PlantListRepository {
    override suspend fun getPlantList(): Flow<Resource<List<Plant>>> {
        return responseHandler.safeApiCall {
            plantListApiService.getPlantList()
        }.mapToDomain { plantListDto ->
            plantListDto.map { it.toDomain() }
        }
    }
}