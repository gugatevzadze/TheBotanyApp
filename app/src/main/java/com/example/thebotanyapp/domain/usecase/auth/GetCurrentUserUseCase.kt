package com.example.thebotanyapp.domain.usecase.auth

import com.example.thebotanyapp.domain.repository.auth.CurrentSessionRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authUserRepository: CurrentSessionRepository
) {
    operator fun invoke() =
        authUserRepository.getCurrentUser(
        )
}