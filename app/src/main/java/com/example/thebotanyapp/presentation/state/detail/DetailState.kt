package com.example.thebotanyapp.presentation.state.detail

import com.example.thebotanyapp.presentation.model.plant.PlantModel

data class DetailState(
    val details: PlantModel? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val isFavourite: Boolean = false
)