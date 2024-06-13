package com.example.thebotanyapp.presentation.event.forgotpassword

sealed class ForgotPasswordEvent {
    data class SendPasswordLink(val email: String) : ForgotPasswordEvent()
    data object ResetErrorMessage : ForgotPasswordEvent()
}