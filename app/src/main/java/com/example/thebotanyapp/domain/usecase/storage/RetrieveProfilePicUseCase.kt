package com.example.thebotanyapp.domain.usecase.storage

import android.net.Uri
import com.example.thebotanyapp.data.common.Resource
import com.example.thebotanyapp.domain.repository.storage.ProfilePicRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrieveProfilePicUseCase @Inject constructor(
    private val profilePicRepository: ProfilePicRepository
) {
    suspend operator fun invoke(uid: String): Flow<Resource<Uri>> {
        return profilePicRepository.retrieveProfilePic(uid)
    }
}