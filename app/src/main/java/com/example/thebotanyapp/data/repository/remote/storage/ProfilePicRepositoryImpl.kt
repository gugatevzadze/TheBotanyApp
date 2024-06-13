package com.example.thebotanyapp.data.repository.remote.storage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.example.thebotanyapp.data.common.Resource
import com.example.thebotanyapp.domain.repository.storage.ProfilePicRepository
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class ProfilePicRepositoryImpl @Inject constructor(
    private val storage: StorageReference,
    private val context: Context
): ProfilePicRepository {

    private suspend fun bitmapFromUri(uri: Uri): Bitmap {
        return withContext(Dispatchers.IO) {
            val inputStream = context.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        }
    }

    override suspend fun uploadProfilePic(uri: Uri, uid: String): Flow<Resource<Boolean>> {
        return flow {
            try {
                val bitmap = bitmapFromUri(uri)
                val fileReference = storage.child("images/$uid/profile_picture.jpg")
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                val data = byteArrayOutputStream.toByteArray()

                val uploadTask = fileReference.putBytes(data)
                uploadTask.await()

                emit(Resource.Success(true))
            } catch (e: Exception) {
                emit(Resource.Error(errorMessage = e.toString()))
            }
        }
    }

    override suspend fun retrieveProfilePic(uid: String): Flow<Resource<Uri>> {
        return flow {
            try {
                val fileReference = storage.child("images/$uid/profile_picture.jpg")
                val downloadUrl = fileReference.downloadUrl.await()

                emit(Resource.Success(downloadUrl))
            } catch (e: Exception) {
                emit(Resource.Error(errorMessage = e.toString()))
            }
        }
    }
}