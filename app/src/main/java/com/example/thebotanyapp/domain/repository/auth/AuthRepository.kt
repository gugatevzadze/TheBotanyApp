package com.example.thebotanyapp.domain.repository.auth

import com.example.thebotanyapp.data.common.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun logIn(email: String, password: String): Flow<Resource<FirebaseUser>>
    suspend fun register(email: String, password: String): Flow<Resource<FirebaseUser>>
    suspend fun logOut(): Flow<Resource<Unit>>
    suspend fun passwordReset(email: String): Flow<Resource<Unit>>
}