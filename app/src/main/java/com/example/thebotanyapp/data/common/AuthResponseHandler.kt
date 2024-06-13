package com.example.thebotanyapp.data.common

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.flow.flow

class AuthResponseHandler {
    suspend fun <T : Any> safeAuthenticationCall(call: suspend () -> T) = flow {
        emit(Resource.Loading(loading = true))
        try {
            val result = call()
            emit(Resource.Success(data = result))
        } catch (e: FirebaseAuthWeakPasswordException) {
            emit(Resource.Error("The password is too weak."))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            emit(Resource.Error("Invalid credentials provided."))
        } catch (e: FirebaseAuthUserCollisionException) {
            emit(Resource.Error("An account with this email already exists."))
        } catch (e: FirebaseAuthInvalidUserException) {
            emit(Resource.Error("No user found with this email."))
        } catch (e: FirebaseAuthEmailException) {
            emit(Resource.Error("There was an error with the email."))
        } catch (e: FirebaseNetworkException) {
            emit(Resource.Error("Check your internet connection and try again."))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
        emit(Resource.Loading(loading = false))
    }
}