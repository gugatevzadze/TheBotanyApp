package com.example.thebotanyapp.presentation.event.welcome

sealed class WelcomeEvent {
    data object LoginButtonClicked : WelcomeEvent()
    data object RegisterButtonClicked : WelcomeEvent()
}