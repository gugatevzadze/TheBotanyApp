package com.example.thebotanyapp.presentation.mapper.plant

import com.example.thebotanyapp.domain.model.plant.Plant
import com.example.thebotanyapp.presentation.model.plant.PlantModel

fun Plant.toPresentation(): PlantModel {
    return PlantModel(
        id = id,
        name = name,
        species = species,
        family = family,
        wateringSchedule = wateringSchedule,
        sunlightRequirement = sunlightRequirement,
        growthHeight = growthHeight,
        bloomingSeason = bloomingSeason,
        description = description,
        image = image
    )
}
fun PlantModel.toDomain() : Plant {
    return Plant(
        id = id,
        name = name,
        species = species,
        family = family,
        wateringSchedule = wateringSchedule,
        sunlightRequirement = sunlightRequirement,
        growthHeight = growthHeight,
        bloomingSeason = bloomingSeason,
        description = description,
        image = image
    )
}
