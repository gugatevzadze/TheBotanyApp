package com.example.thebotanyapp.domain.usecase.plant


import com.example.thebotanyapp.data.common.Resource
import com.example.thebotanyapp.domain.model.plant.Plant
import com.example.thebotanyapp.domain.repository.plant.PlantDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlantDetailUseCase @Inject constructor(private val plantDetailsRepository: PlantDetailsRepository) {
    suspend operator fun invoke(plantId: Int): Flow<Resource<Plant>>{
        return plantDetailsRepository.getPlantDetails(plantId)
    }
}