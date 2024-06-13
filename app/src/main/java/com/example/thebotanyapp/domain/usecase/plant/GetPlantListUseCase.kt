package com.example.thebotanyapp.domain.usecase.plant

import com.example.thebotanyapp.data.common.Resource
import com.example.thebotanyapp.domain.model.plant.Plant
import com.example.thebotanyapp.domain.repository.plant.PlantListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlantListUseCase @Inject constructor(private val plantListRepository: PlantListRepository) {
    suspend operator fun invoke(): Flow<Resource<List<Plant>>>{
        return plantListRepository.getPlantList()
    }
}