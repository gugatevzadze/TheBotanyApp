package com.example.thebotanyapp.domain.usecase.favourites

import com.example.thebotanyapp.domain.repository.firestore.user.UserFavouritesRepository
import javax.inject.Inject

class RetrieveFavouritePlantUseCase @Inject constructor(
    private val favouritesRepository: UserFavouritesRepository
) {
    suspend operator fun invoke() = favouritesRepository.retrieveFavouritePlants()
}