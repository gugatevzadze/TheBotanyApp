package com.example.thebotanyapp.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thebotanyapp.domain.usecase.auth.LogoutUseCase
import com.example.thebotanyapp.domain.usecase.datastore.ClearSessionDataStoreUseCase
import com.example.thebotanyapp.presentation.event.home.HomeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val clearSessionDataStoreUseCase: ClearSessionDataStoreUseCase
): ViewModel() {

    private val _homeNavigationEvent = MutableSharedFlow<HomeNavigationEvent>()
    val homeNavigationEvent: SharedFlow<HomeNavigationEvent> get() = _homeNavigationEvent

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Logout -> logout()
        }
    }

    private fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            clearSessionDataStoreUseCase()
            _homeNavigationEvent.emit(HomeNavigationEvent.NavigateToWelcome)
        }
    }

    sealed interface HomeNavigationEvent {
        data object NavigateToWelcome : HomeNavigationEvent
    }
}