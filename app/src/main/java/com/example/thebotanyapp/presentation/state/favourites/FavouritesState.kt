package com.example.thebotanyapp.presentation.state.favourites

import com.example.thebotanyapp.presentation.model.plant.PlantModel

data class FavouritesState(
    val favourites: List<PlantModel>? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val originalFavourites: List<PlantModel>? = null
)
