package com.example.thebotanyapp.data.repository.remote.auth

import com.example.thebotanyapp.data.common.AuthResponseHandler
import com.example.thebotanyapp.data.common.Resource
import com.example.thebotanyapp.domain.repository.auth.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val authResponseHandler: AuthResponseHandler
): AuthRepository {
    override suspend fun logIn(email: String, password: String): Flow<Resource<FirebaseUser>> {
        return authResponseHandler.safeAuthenticationCall {
            firebaseAuth.signInWithEmailAndPassword(email, password).await().user!!
        }
    }

    override suspend fun register(
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser>> {
        return authResponseHandler.safeAuthenticationCall {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await().user!!
        }
    }

    override suspend fun logOut(): Flow<Resource<Unit>> {
        return authResponseHandler.safeAuthenticationCall {
            firebaseAuth.signOut()
        }
    }

    override suspend fun passwordReset(email: String): Flow<Resource<Unit>> {
        return authResponseHandler.safeAuthenticationCall {
            firebaseAuth.sendPasswordResetEmail(email).await()
        }
    }
}
