package com.example.thebotanyapp.presentation.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thebotanyapp.data.common.Resource
import com.example.thebotanyapp.domain.usecase.favourites.AddFavouritePlantUseCase
import com.example.thebotanyapp.domain.usecase.favourites.RemoveFavouritePlantUseCase
import com.example.thebotanyapp.domain.usecase.plant.GetPlantDetailUseCase
import com.example.thebotanyapp.presentation.event.detail.DetailEvent
import com.example.thebotanyapp.presentation.mapper.plant.toDomain
import com.example.thebotanyapp.presentation.mapper.plant.toPresentation
import com.example.thebotanyapp.presentation.model.plant.PlantModel
import com.example.thebotanyapp.presentation.state.detail.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getPlantDetailUseCase: GetPlantDetailUseCase,
    private val addFavouritePlantUseCase: AddFavouritePlantUseCase
) : ViewModel() {

    private val _detailState = MutableStateFlow(DetailState())

    val detailState: SharedFlow<DetailState> get() = _detailState

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.GetPlantDetail -> getPlantDetail(event.plantId)
            is DetailEvent.AddFavouritePlant -> addFavouritePlant(event.plant)
        }
    }

    private fun getPlantDetail(plantId: Int) {
        viewModelScope.launch {
            getPlantDetailUseCase.invoke(plantId).collect {
                when (it) {
                    is Resource.Success -> {
                        _detailState.update { currentState ->
                            currentState.copy(
                                details = it.data.toPresentation()
                            )
                        }
                    }

                    is Resource.Error -> {
                        _detailState.update { currentState ->
                            currentState.copy(
                                errorMessage = it.errorMessage
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _detailState.update { currentState ->
                            currentState.copy(
                                isLoading = it.loading
                            )
                        }
                    }
                }
            }
        }
    }

    private fun addFavouritePlant(plant: PlantModel) {
        viewModelScope.launch {
            addFavouritePlantUseCase.invoke(plant.toDomain()).collect {
                when (it) {
                    is Resource.Success -> {
                        _detailState.update { currentState ->
                            currentState.copy()
                        }
                    }

                    is Resource.Error -> {
                        _detailState.update { currentState ->
                            currentState.copy(
                                errorMessage = it.errorMessage
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _detailState.update { currentState ->
                            currentState.copy(
                                isLoading = it.loading
                            )
                        }
                    }
                }
            }
        }
    }
}