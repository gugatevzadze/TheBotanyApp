package com.example.thebotanyapp.presentation.event.favourites

import com.example.thebotanyapp.presentation.model.plant.PlantModel

sealed class FavouritesEvent {
    data object GetFavouritesList: FavouritesEvent()
    data class RemovePlantFromFavourite(val plant: PlantModel): FavouritesEvent()
    data class FavouritePlantSearch(val query: String) : FavouritesEvent()
    data class PlantItemClicked(val plant: PlantModel) : FavouritesEvent()
}