package com.example.thebotanyapp.presentation.state.list

import com.example.thebotanyapp.presentation.model.plant.PlantModel


data class ListState(
    val plants: List<PlantModel>? = null,
    val originalPlants: List<PlantModel>? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)