package com.example.thebotanyapp.presentation.screen.welcome

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thebotanyapp.domain.usecase.datastore.ReadSessionDataStoreUseCase
import com.example.thebotanyapp.presentation.event.welcome.WelcomeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor() :
    ViewModel() {
    private val _welcomeNavigationEvent = MutableSharedFlow<WelcomeNavigationEvent>()
    val welcomeNavigationEvent: SharedFlow<WelcomeNavigationEvent> get() = _welcomeNavigationEvent

    fun onEvent(event: WelcomeEvent) {
        when (event) {
            is WelcomeEvent.LoginButtonClicked -> navigateToLogin()
            is WelcomeEvent.RegisterButtonClicked -> navigateToRegister()
        }
    }

    private fun navigateToLogin(){
        viewModelScope.launch {
            _welcomeNavigationEvent.emit(WelcomeNavigationEvent.NavigateToLogin)
        }
    }

    private fun navigateToRegister(){
        viewModelScope.launch {
            _welcomeNavigationEvent.emit(WelcomeNavigationEvent.NavigateToRegister)
        }
    }


    sealed interface WelcomeNavigationEvent{
        data object NavigateToLogin : WelcomeNavigationEvent
        data object NavigateToRegister: WelcomeNavigationEvent
    }
}