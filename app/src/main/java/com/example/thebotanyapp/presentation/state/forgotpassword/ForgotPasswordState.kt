package com.example.thebotanyapp.presentation.state.forgotpassword

import com.example.thebotanyapp.data.common.Resource


data class ForgotPasswordState(
    val state: Resource<Unit>?,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)