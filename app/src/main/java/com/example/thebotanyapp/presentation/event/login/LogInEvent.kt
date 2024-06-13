package com.example.thebotanyapp.presentation.event.login

sealed class LogInEvent {
    data class LogIn(val email: String, val password: String) : LogInEvent()
    data class LogInWithRememberMe(val email: String, val password: String) : LogInEvent()
    data object PasswordResetClicked : LogInEvent()
    data object ResetErrorMessage : LogInEvent()
}