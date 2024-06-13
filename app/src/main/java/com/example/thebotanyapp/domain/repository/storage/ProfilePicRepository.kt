package com.example.thebotanyapp.domain.repository.storage

import android.net.Uri
import com.example.thebotanyapp.data.common.Resource
import kotlinx.coroutines.flow.Flow

interface ProfilePicRepository {

    suspend fun uploadProfilePic(uri: Uri, uid: String): Flow<Resource<Boolean>>

    suspend fun retrieveProfilePic(uid: String): Flow<Resource<Uri>>
}