package com.example.thebotanyapp.data.repository.remote.auth

import com.example.thebotanyapp.domain.repository.auth.CurrentSessionRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class CurrentSessionRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): CurrentSessionRepository {
    override fun getCurrentUser(): String? {
        return firebaseAuth.currentUser?.uid
    }
}