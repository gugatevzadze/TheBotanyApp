package com.example.thebotanyapp.presentation.event.detail

import com.example.thebotanyapp.presentation.model.plant.PlantModel


sealed class DetailEvent {
    data class GetPlantDetail(val plantId: Int) : DetailEvent()
    data class AddFavouritePlant(val plant: PlantModel) : DetailEvent()
}