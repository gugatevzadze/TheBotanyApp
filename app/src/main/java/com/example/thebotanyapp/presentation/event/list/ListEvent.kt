package com.example.thebotanyapp.presentation.event.list

import com.example.thebotanyapp.presentation.model.plant.PlantModel


sealed class ListEvent {
    data object GetPlantList : ListEvent()
    data class PlantItemClick(val plant: PlantModel) : ListEvent()
    data class PlantSearch(val query: String) : ListEvent()
}