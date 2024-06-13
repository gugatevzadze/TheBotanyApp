package com.example.thebotanyapp.presentation.screen.favourites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thebotanyapp.data.common.Resource
import com.example.thebotanyapp.domain.usecase.favourites.RemoveFavouritePlantUseCase
import com.example.thebotanyapp.domain.usecase.favourites.RetrieveFavouritePlantUseCase
import com.example.thebotanyapp.presentation.event.favourites.FavouritesEvent
import com.example.thebotanyapp.presentation.mapper.plant.toDomain
import com.example.thebotanyapp.presentation.mapper.plant.toPresentation
import com.example.thebotanyapp.presentation.model.plant.PlantModel
import com.example.thebotanyapp.presentation.state.favourites.FavouritesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val retrieveFavouritePlantUseCase: RetrieveFavouritePlantUseCase,
    private val removeFavouritePlantUseCase: RemoveFavouritePlantUseCase
) : ViewModel() {
    private val _favouritePlant = MutableStateFlow(FavouritesState())
    val favouritePlant: StateFlow<FavouritesState> get() = _favouritePlant

    private val _favouritesNavigationEvents = MutableSharedFlow<FavouritesNavigationEvents>()
    val favouritesNavigationEvents: SharedFlow<FavouritesNavigationEvents> get() = _favouritesNavigationEvents

    fun onEvent(event: FavouritesEvent) {
        when (event) {
            is FavouritesEvent.GetFavouritesList -> retrieveFavouritePlants()
            is FavouritesEvent.RemovePlantFromFavourite -> removeFavouritePlant(event.plant)
            is FavouritesEvent.FavouritePlantSearch -> onFavouritePlantSearch(event.query)
            is FavouritesEvent.PlantItemClicked -> navigateToPlantDetails(event.plant)
        }
    }

    private fun retrieveFavouritePlants() {
        viewModelScope.launch {
            Log.d("FavouritesViewModel", "Retrieving favourite plants")
            retrieveFavouritePlantUseCase.invoke().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val favourites = resource.data.map { it.toPresentation() }
                        _favouritePlant.update { currentState ->
                            currentState.copy(
                                favourites = favourites,
                                originalFavourites = favourites
                            )
                        }
                        Log.d("FavouritesViewModel", "Successfully retrieved favourite plants: $favourites")
                    }
                    is Resource.Error -> {
                        _favouritePlant.update { currentState ->
                            currentState.copy(
                                errorMessage = resource.errorMessage
                            )
                        }
                        Log.d("FavouritesViewModel", "Error retrieving favourite plants: ${resource.errorMessage}")
                    }
                    is Resource.Loading -> {
                        _favouritePlant.update { currentState ->
                            currentState.copy(
                                isLoading = resource.loading
                            )
                        }
                    }
                }
            }
        }
    }
    private fun removeFavouritePlant(plant: PlantModel) {
        viewModelScope.launch {
            removeFavouritePlantUseCase.invoke(plant.toDomain()).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _favouritePlant.update { currentState ->
                            currentState.copy(
                                favourites = currentState.favourites?.filter { it.id != plant.id },
                                originalFavourites = currentState.originalFavourites?.filter { it.id != plant.id }
                            )
                        }
                    }
                    is Resource.Error -> {
                        _favouritePlant.update { currentState ->
                            currentState.copy(
                                errorMessage = resource.errorMessage
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _favouritePlant.update { currentState ->
                            currentState.copy(
                                isLoading = resource.loading
                            )
                        }
                    }
                }
            }
        }
    }

    private fun onFavouritePlantSearch(query: String) {
        viewModelScope.launch {
            _favouritePlant.update { currentState ->
                val originalList = currentState.originalFavourites ?: currentState.favourites.orEmpty()
                val filteredList = if (query.isEmpty()) {
                    originalList
                } else {
                    originalList.filter { it.name.contains(query, true) }
                }
                currentState.copy(
                    favourites = filteredList,
                    originalFavourites = currentState.originalFavourites ?: currentState.favourites.orEmpty()
                )
            }
        }
    }

    private fun navigateToPlantDetails(plant: PlantModel) {
        viewModelScope.launch {
            _favouritesNavigationEvents.emit(FavouritesNavigationEvents.NavigateToPlantDetails(plant.id))
        }
    }

    sealed interface FavouritesNavigationEvents{
        data class NavigateToPlantDetails(val plantId: Int): FavouritesNavigationEvents
    }
}