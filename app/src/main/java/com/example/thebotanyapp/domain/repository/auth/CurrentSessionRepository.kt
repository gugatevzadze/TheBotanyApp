package com.example.thebotanyapp.domain.repository.auth

interface CurrentSessionRepository {
    fun getCurrentUser(): String?
}