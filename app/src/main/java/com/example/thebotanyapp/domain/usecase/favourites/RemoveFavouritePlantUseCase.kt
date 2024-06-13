package com.example.thebotanyapp.domain.usecase.favourites

import com.example.thebotanyapp.domain.model.plant.Plant
import com.example.thebotanyapp.domain.repository.firestore.user.UserFavouritesRepository
import javax.inject.Inject

class RemoveFavouritePlantUseCase @Inject constructor(
    private val favouritesRepository: UserFavouritesRepository
) {
    suspend operator fun invoke(plant: Plant) = favouritesRepository.removeFavouritePlant(plant = plant)
}