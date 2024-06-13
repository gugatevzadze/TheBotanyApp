package com.example.thebotanyapp.domain.usecase.validator

class PasswordValidatorUseCase {
    fun isPasswordValid(password: String): Boolean {
        val minLength = 8
        val hasUppercase = password.any { it.isUpperCase() }
        val hasLowercase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        return password.length >= minLength && hasUppercase && hasLowercase && hasDigit
    }
}