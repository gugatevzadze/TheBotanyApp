package com.example.thebotanyapp.domain.usecase.auth

import com.example.thebotanyapp.domain.repository.auth.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) =
        authRepository.logIn(email = email, password = password)
}