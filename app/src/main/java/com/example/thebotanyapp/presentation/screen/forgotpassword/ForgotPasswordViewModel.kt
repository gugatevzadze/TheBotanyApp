package com.example.thebotanyapp.presentation.screen.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thebotanyapp.data.common.Resource
import com.example.thebotanyapp.domain.usecase.auth.ResetPasswordUseCase
import com.example.thebotanyapp.domain.usecase.validator.EmailValidatorUseCase
import com.example.thebotanyapp.presentation.event.forgotpassword.ForgotPasswordEvent
import com.example.thebotanyapp.presentation.state.forgotpassword.ForgotPasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val emailValidatorUseCase: EmailValidatorUseCase
) : ViewModel() {

    private val _forgotPasswordState = MutableStateFlow(ForgotPasswordState(null))
    val forgotPasswordState: SharedFlow<ForgotPasswordState> = _forgotPasswordState

    private val _navigationEvent = MutableSharedFlow<ForgotPasswordNavigationEvent>()
    val navigationEvent: SharedFlow<ForgotPasswordNavigationEvent> get() = _navigationEvent


    fun onEvent(event: ForgotPasswordEvent) {
        when (event) {
            is ForgotPasswordEvent.SendPasswordLink -> resetPassword(email = event.email)
            is ForgotPasswordEvent.ResetErrorMessage -> updateErrorMessage(message = null)
        }
    }

    private fun resetPassword(email: String) {
        viewModelScope.launch {
            if (validationChecker(email)) {
                resetPasswordUseCase(email = email).collect {
                    when (it) {
                        is Resource.Loading -> _forgotPasswordState.update { currentState ->
                            currentState.copy(
                                isLoading = it.loading
                            )
                        }

                        is Resource.Success -> {
                            _forgotPasswordState.update { currentState -> currentState.copy() }
                            _navigationEvent.emit(ForgotPasswordNavigationEvent.NavigateToLogin)
                        }

                        is Resource.Error -> {
                            _forgotPasswordState.update { currentState ->
                                currentState.copy(
                                    isLoading = false,
                                    errorMessage = it.errorMessage
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun validationChecker(email: String): Boolean {
        return when {
            !emailValidatorUseCase.isEmailValid(email) -> {
                updateErrorMessage(message = "Please enter a valid email address")
                false
            }
            else -> true
        }
    }


    private fun updateErrorMessage(message: String?) {
        _forgotPasswordState.value = _forgotPasswordState.value.copy(errorMessage = message)
    }

    sealed interface ForgotPasswordNavigationEvent {
        data object NavigateToLogin : ForgotPasswordNavigationEvent
    }
}