package com.example.thebotanyapp.presentation.event.register


sealed class RegisterEvent {
    data class Register(val email: String, val password: String, val confirmPassword: String) : RegisterEvent()
    data object ResetErrorMessage : RegisterEvent()
}