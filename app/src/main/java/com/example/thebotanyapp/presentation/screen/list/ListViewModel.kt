package com.example.thebotanyapp.presentation.screen.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thebotanyapp.data.common.Resource
import com.example.thebotanyapp.domain.model.plant.Plant
import com.example.thebotanyapp.domain.usecase.database.GetDatabaseUseCase
import com.example.thebotanyapp.domain.usecase.plant.GetPlantListUseCase
import com.example.thebotanyapp.presentation.event.list.ListEvent
import com.example.thebotanyapp.presentation.mapper.plant.toPresentation
import com.example.thebotanyapp.presentation.model.plant.PlantModel
import com.example.thebotanyapp.presentation.state.list.ListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getPlantListUseCase: GetPlantListUseCase,
    private val getDatabaseUseCase: GetDatabaseUseCase
) : ViewModel() {

    private val _listState = MutableStateFlow(ListState())
    val listState: SharedFlow<ListState> get() = _listState

    private val _listNavigationEvent = MutableSharedFlow<ListNavigationEvent>()
    val listNavigationEvent: SharedFlow<ListNavigationEvent> get() = _listNavigationEvent

    fun onEvent(event: ListEvent) {
        when (event) {
            is ListEvent.GetPlantList -> getPlantList()
            is ListEvent.PlantItemClick -> onPlantItemClick(event.plant)
            is ListEvent.PlantSearch -> onPlantSearch(event.query)
        }
    }

    private fun getPlantList() {
        viewModelScope.launch {
            getDatabaseUseCase.invoke().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val plants = resource.data.map { it.toPresentation() }
                        _listState.update { currentState ->
                            currentState.copy(plants = plants)
                        }
                    }
                    is Resource.Error -> {
                        _listState.update { currentState ->
                            currentState.copy(
                                errorMessage = resource.errorMessage
                            )
                        }
                    }
                    is Resource.Loading -> {
                        //
                    }
                }
                Log.d("ListViewModel", "Current state: ${_listState.value}")
            }
        }
    }

//    private fun getPlantList() {
//        viewModelScope.launch {
//            getPlantListUseCase.invoke().collect { it ->
//                when (it) {
//                    is Resource.Success -> {
//                        _listState.update { currentState ->
//                            currentState.copy(
//                                plants = it.data.map { it.toPresentation() }
//                            )
//                        }
//                    }
//
//                    is Resource.Error -> {
//                        _listState.update { currentState ->
//                            currentState.copy(
//                                errorMessage = it.errorMessage
//                            )
//                        }
//                    }
//
//                    is Resource.Loading -> {
//                        _listState.update { currentState ->
//                            currentState.copy(
//                                isLoading = it.loading
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }

    private fun onPlantItemClick(plant: PlantModel) {
        viewModelScope.launch {
            _listNavigationEvent.emit(ListNavigationEvent.NavigateToDetail(plant.id))
        }
    }

    private fun onPlantSearch(query: String) {
        viewModelScope.launch {
            _listState.update { currentState ->
                val originalList = currentState.originalPlants ?: currentState.plants.orEmpty()
                val filteredList = if (query.isEmpty()) {
                    originalList
                } else {
                    originalList.filter { it.name.contains(query, true) }
                }
                currentState.copy(
                    plants = filteredList,
                    originalPlants = currentState.originalPlants ?: currentState.plants.orEmpty()
                )
            }
        }
    }

    sealed interface ListNavigationEvent {
        data class NavigateToDetail(val plantId: Int) : ListNavigationEvent
    }
}