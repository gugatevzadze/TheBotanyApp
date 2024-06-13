package com.example.thebotanyapp.presentation.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thebotanyapp.data.common.Resource
import com.example.thebotanyapp.domain.usecase.auth.RegisterUseCase
import com.example.thebotanyapp.domain.usecase.validator.EmailValidatorUseCase
import com.example.thebotanyapp.domain.usecase.validator.FieldsValidatorUseCase
import com.example.thebotanyapp.domain.usecase.validator.PasswordMatchingUseCase
import com.example.thebotanyapp.domain.usecase.validator.PasswordValidatorUseCase
import com.example.thebotanyapp.presentation.event.register.RegisterEvent
import com.example.thebotanyapp.presentation.state.register.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val emailValidatorUseCase: EmailValidatorUseCase,
    private val passwordValidatorUseCase: PasswordValidatorUseCase,
    private val passwordMatchingUseCase: PasswordMatchingUseCase,
    private val fieldsValidatorUseCase: FieldsValidatorUseCase
) :ViewModel() {

    private val _registerState = MutableStateFlow(RegisterState())
    val registerState: SharedFlow<RegisterState> = _registerState.asStateFlow()

    private val _registerNavigationEvent = MutableSharedFlow<RegisterNavigationEvent>()
    val registerNavigationEvent: SharedFlow<RegisterNavigationEvent> get() = _registerNavigationEvent

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.Register -> register(email = event.email, password = event.password, confirmPassword = event.confirmPassword)
            is RegisterEvent.ResetErrorMessage -> updateErrorMessage(message = null)
        }
    }

    private fun register(email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            if (validationChecker(email, password, confirmPassword)) {
                registerUseCase(email = email, password = password).collect {
                    when (it) {
                        is Resource.Loading -> _registerState.update { currentState ->
                            currentState.copy(
                                isLoading = it.loading
                            )
                        }

                        is Resource.Success -> {
                            _registerState.update { currentState -> currentState.copy() }
                            _registerNavigationEvent.emit(RegisterNavigationEvent.NavigateToLogin)
                        }

                        is Resource.Error -> {
                            _registerState.update { currentState ->
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

    private fun validationChecker(email: String, password: String, confirmPassword: String): Boolean {
        return when {
            !fieldsValidatorUseCase.validateFields(email, password) -> {
                updateErrorMessage(message = "Please fill in all fields")
                false
            }
            !emailValidatorUseCase.isEmailValid(email) -> {
                updateErrorMessage(message = "Please enter a valid email address")
                false
            }
            !passwordValidatorUseCase.isPasswordValid(password) -> {
                updateErrorMessage(message = "Please enter a valid password")
                false
            }
            !passwordMatchingUseCase.passwordsMatch(password, confirmPassword) -> {
                updateErrorMessage(message = "Passwords do not match")
                false
            }
            else -> true
        }
    }

    private fun updateErrorMessage(message: String?) {
        _registerState.update { currentState -> currentState.copy(errorMessage = message) }
    }

    sealed interface RegisterNavigationEvent {
        data object NavigateToLogin : RegisterNavigationEvent
    }
}