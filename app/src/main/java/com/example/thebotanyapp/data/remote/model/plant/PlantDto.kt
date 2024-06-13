package com.example.thebotanyapp.data.remote.model.plant

import com.squareup.moshi.Json

data class PlantDto(
    val id: Int,
    val name: String,
    val species: String,
    val family: String,
    @Json(name = "watering_schedule")
    val wateringSchedule: String,
    @Json(name = "sunlight_requirement")
    val sunlightRequirement: String,
    @Json(name = "growth_height")
    val growthHeight: String,
    @Json(name = "blooming_season")
    val bloomingSeason: String,
    val description: String,
    val image: String
)