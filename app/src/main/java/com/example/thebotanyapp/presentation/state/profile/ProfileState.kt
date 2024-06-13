package com.example.thebotanyapp.presentation.state.profile

import android.net.Uri

data class ProfileState(
    val profileImage : Uri? = null,
    val isLoading : Boolean = false,
    val errorMessage : String? = null
)