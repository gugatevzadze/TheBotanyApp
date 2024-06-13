package com.example.thebotanyapp.domain.usecase.validator

class PasswordMatchingUseCase {
    fun passwordsMatch(passwordOne: String, passwordTwo: String): Boolean {
        return passwordOne == passwordTwo
    }
}