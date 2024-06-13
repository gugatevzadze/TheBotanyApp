package com.example.thebotanyapp.domain.usecase.storage

import android.net.Uri
import com.example.thebotanyapp.data.common.Resource
import com.example.thebotanyapp.domain.repository.storage.ProfilePicRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UploadProfilePicUseCase @Inject constructor(
    private val profilePicRepository: ProfilePicRepository
) {
    suspend operator fun invoke(uri: Uri, uid: String): Flow<Resource<Boolean>> {
        return profilePicRepository.uploadProfilePic(uri, uid)
    }
}