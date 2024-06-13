package com.example.thebotanyapp.domain.model.plant

data class Plant(
    val id: Int,
    val name: String,
    val species: String,
    val family: String,
    val wateringSchedule: String,
    val sunlightRequirement: String,
    val growthHeight: String,
    val bloomingSeason: String,
    val description: String,
    val image: String
)
