package com.example.thebotanyapp.presentation.screen.session

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thebotanyapp.domain.usecase.datastore.ReadSessionDataStoreUseCase
import com.example.thebotanyapp.presentation.screen.welcome.WelcomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val readSessionDataStoreUseCase: ReadSessionDataStoreUseCase) : ViewModel(){

    private val _sessionNavigationEvent = MutableSharedFlow<SessionNavigationEvent>()
    val sessionNavigationEvent: SharedFlow<SessionNavigationEvent> get() = _sessionNavigationEvent

    init {
        readSession()
    }

    private fun readSession() {
        viewModelScope.launch {
            readSessionDataStoreUseCase().collect { isSessionActive ->
                if (isSessionActive) {
                    navigateToHome()
                } else{
                    navigateToWelcome()
                }
            }
        }
    }

    private fun navigateToHome(){
        viewModelScope.launch {
            _sessionNavigationEvent.emit(SessionNavigationEvent.NavigateToHome)
        }
    }

    private fun navigateToWelcome(){
        viewModelScope.launch {
            _sessionNavigationEvent.emit(SessionNavigationEvent.NavigateToWelcome)
        }
    }

    sealed interface SessionNavigationEvent{
        data object NavigateToHome: SessionNavigationEvent
        data object NavigateToWelcome: SessionNavigationEvent
    }
}