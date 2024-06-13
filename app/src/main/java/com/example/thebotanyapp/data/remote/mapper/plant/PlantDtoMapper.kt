package com.example.thebotanyapp.data.remote.mapper.plant

import com.example.thebotanyapp.data.remote.model.plant.PlantDto
import com.example.thebotanyapp.domain.model.plant.Plant

fun PlantDto.toDomain(): Plant {
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