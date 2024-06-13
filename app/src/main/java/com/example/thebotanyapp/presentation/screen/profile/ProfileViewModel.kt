package com.example.thebotanyapp.presentation.screen.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thebotanyapp.data.common.Resource
import com.example.thebotanyapp.domain.usecase.auth.GetCurrentUserUseCase
import com.example.thebotanyapp.domain.usecase.storage.RetrieveProfilePicUseCase
import com.example.thebotanyapp.domain.usecase.storage.UploadProfilePicUseCase
import com.example.thebotanyapp.presentation.event.profile.ProfileEvent
import com.example.thebotanyapp.presentation.state.profile.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val uploadProfilePicUseCase: UploadProfilePicUseCase,
    private val retrieveProfilePicUseCase: RetrieveProfilePicUseCase,
    private val currentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: SharedFlow<ProfileState> = _profileState.asStateFlow()

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.UpdateProfilePic -> uploadProfilePic(event.uri)
            is ProfileEvent.RetrieveProfilePic -> retrieveProfilePic()
        }
    }

    private fun uploadProfilePic(uri: Uri) {
        val uid = currentUserUseCase()
        viewModelScope.launch {
            uploadProfilePicUseCase(uri, uid!!).collect {
                when (it) {
                    is Resource.Loading -> _profileState.update { currentState ->
                        currentState.copy(
                            isLoading = it.loading
                        )
                    }
                    is Resource.Success -> {
                        _profileState.update { currentState -> currentState.copy() }
                    }
                    is Resource.Error -> {
                        _profileState.update { currentState ->
                            currentState.copy(
                                errorMessage = it.errorMessage
                            )
                        }
                    }
                }
            }
        }
    }

    private fun retrieveProfilePic() {
        val uid = currentUserUseCase()
        viewModelScope.launch {
            retrieveProfilePicUseCase(uid!!).collect {
                when (it) {
                    is Resource.Loading -> _profileState.update { currentState ->
                        currentState.copy(
                            isLoading = it.loading
                        )
                    }
                    is Resource.Success -> {
                        _profileState.update { currentState ->
                            currentState.copy(
                                profileImage = it.data
                            )
                        }
                    }
                    is Resource.Error -> {
                        _profileState.update { currentState ->
                            currentState.copy(
                                errorMessage = it.errorMessage
                            )
                        }
                    }
                }
            }
        }
    }
}