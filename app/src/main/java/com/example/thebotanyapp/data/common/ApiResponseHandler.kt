package com.example.thebotanyapp.data.common

import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

class ApiResponseHandler {
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>) = flow {
        emit(Resource.Loading(loading = true))
        try {
            val response = call()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                emit(Resource.Success(data = body))
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Error Occurred"
                emit(Resource.Error(errorMessage = errorMessage))
            }
        } catch (e: SocketTimeoutException) {
            emit(Resource.Error("The server did not respond in time."))
        } catch (e: UnknownHostException) {
            emit(Resource.Error("The server could not be found."))
        } catch (e: SSLHandshakeException) {
            emit(Resource.Error("There was an error establishing a secure connection."))
        } catch (e: HttpException) {
            emit(Resource.Error("There was an error with the HTTP request."))
        } catch (e: IOException) {
            emit(Resource.Error("There was an input/output error."))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown Error Occurred"))
        }
        emit(Resource.Loading(loading = false))
    }
}

