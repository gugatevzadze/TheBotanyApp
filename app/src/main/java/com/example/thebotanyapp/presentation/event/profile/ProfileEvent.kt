package com.example.thebotanyapp.presentation.event.profile

import android.net.Uri

sealed class ProfileEvent {
    data object RetrieveProfilePic : ProfileEvent()
    data class UpdateProfilePic(val uri: Uri) : ProfileEvent()
}