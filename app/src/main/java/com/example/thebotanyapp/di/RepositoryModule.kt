package com.example.thebotanyapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.thebotanyapp.data.common.ApiResponseHandler
import com.example.thebotanyapp.data.common.AuthResponseHandler
import com.example.thebotanyapp.data.remote.service.plant.PlantDetailsApiService
import com.example.thebotanyapp.data.remote.service.plant.PlantListApiService
import com.example.thebotanyapp.data.repository.remote.auth.AuthRepositoryImpl
import com.example.thebotanyapp.data.repository.remote.auth.CurrentSessionRepositoryImpl
import com.example.thebotanyapp.data.repository.local.datastore.DataStoreRepositoryImpl
import com.example.thebotanyapp.data.repository.remote.firestore.user.UserFavouritesRepositoryImpl
import com.example.thebotanyapp.data.repository.remote.plant.PlantDetailsRepositoryImpl
import com.example.thebotanyapp.data.repository.remote.plant.PlantListRepositoryImpl
import com.example.thebotanyapp.data.repository.remote.storage.ProfilePicRepositoryImpl
import com.example.thebotanyapp.domain.repository.auth.AuthRepository
import com.example.thebotanyapp.domain.repository.auth.CurrentSessionRepository
import com.example.thebotanyapp.domain.repository.datastore.DataStoreRepository
import com.example.thebotanyapp.domain.repository.firestore.user.UserFavouritesRepository
import com.example.thebotanyapp.domain.repository.plant.PlantDetailsRepository
import com.example.thebotanyapp.domain.repository.plant.PlantListRepository
import com.example.thebotanyapp.domain.repository.storage.ProfilePicRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStoreRepository(dataStore: DataStore<Preferences>): DataStoreRepository {
        return DataStoreRepositoryImpl(dataStore = dataStore)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth, authResponseHandler: AuthResponseHandler): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth = firebaseAuth, authResponseHandler = authResponseHandler)
    }

    @Provides
    @Singleton
    fun provideAuthUserRepository(
        firebaseAuth: FirebaseAuth
    ): CurrentSessionRepository {
        return CurrentSessionRepositoryImpl(firebaseAuth = firebaseAuth)
    }

    @Provides
    @Singleton
    fun providePlantListRepository(
        plantListApiService: PlantListApiService,
        responseHandler: ApiResponseHandler
    ): PlantListRepository {
        return PlantListRepositoryImpl(
            plantListApiService = plantListApiService,
            responseHandler = responseHandler
        )
    }

    @Provides
    @Singleton
    fun providePlantDetailsRepository(
        plantDetailsApiService: PlantDetailsApiService,
        responseHandler: ApiResponseHandler
    ): PlantDetailsRepository {
        return PlantDetailsRepositoryImpl(
            plantDetailsApiService = plantDetailsApiService,
            responseHandler = responseHandler
        )
    }

    @Provides
    @Singleton
    fun provideUserFavoriteRepository(
        db: FirebaseFirestore,
        user: FirebaseAuth
    ): UserFavouritesRepository {
        return UserFavouritesRepositoryImpl(db = db, user = user)
    }


    @Provides
    @Singleton
    fun provideProfilePicRepository(
        storage: StorageReference,
        @ApplicationContext context: Context
    ): ProfilePicRepository {
        return ProfilePicRepositoryImpl(storage = storage, context = context)
    }
}