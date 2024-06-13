package com.example.thebotanyapp.di

import com.example.thebotanyapp.domain.repository.auth.AuthRepository
import com.example.thebotanyapp.domain.repository.datastore.DataStoreRepository
import com.example.thebotanyapp.domain.usecase.auth.LoginUseCase
import com.example.thebotanyapp.domain.usecase.auth.LogoutUseCase
import com.example.thebotanyapp.domain.usecase.auth.RegisterUseCase
import com.example.thebotanyapp.domain.usecase.datastore.ClearSessionDataStoreUseCase
import com.example.thebotanyapp.domain.usecase.datastore.ReadSessionDataStoreUseCase
import com.example.thebotanyapp.domain.usecase.datastore.SaveSessionDataStoreUseCase
import com.example.thebotanyapp.domain.usecase.validator.EmailValidatorUseCase
import com.example.thebotanyapp.domain.usecase.validator.FieldsValidatorUseCase
import com.example.thebotanyapp.domain.usecase.validator.PasswordMatchingUseCase
import com.example.thebotanyapp.domain.usecase.validator.PasswordValidatorUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideLoginUseCase(
        authRepository: AuthRepository
    ): LoginUseCase {
        return LoginUseCase(
            authRepository = authRepository
        )
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(
        authRepository: AuthRepository
    ): RegisterUseCase {
        return RegisterUseCase(
            authRepository = authRepository
        )
    }

    @Provides
    @Singleton
    fun provideLogoutUseCase(
        authRepository: AuthRepository
    ): LogoutUseCase {
        return LogoutUseCase(
            authRepository = authRepository
        )
    }

    @Provides
    @Singleton
    fun provideClearSessionDataStoreUseCase(
        dataStoreRepository: DataStoreRepository
    ): ClearSessionDataStoreUseCase {
        return ClearSessionDataStoreUseCase(
            dataStoreRepository = dataStoreRepository
        )
    }

    @Provides
    @Singleton
    fun provideReadSessionDataStoreUseCase(
        dataStoreRepository: DataStoreRepository
    ): ReadSessionDataStoreUseCase {
        return ReadSessionDataStoreUseCase(
            dataStoreRepository = dataStoreRepository
        )
    }

    @Provides
    @Singleton
    fun provideSaveSessionDataStoreUseCase(
        dataStoreRepository: DataStoreRepository
    ): SaveSessionDataStoreUseCase {
        return SaveSessionDataStoreUseCase(
            dataStoreRepository = dataStoreRepository
        )
    }

    @Provides
    @Singleton
    fun provideEmailValidatorUseCase(
    ): EmailValidatorUseCase {
        return EmailValidatorUseCase()
    }

    @Provides
    @Singleton
    fun provideFieldsValidatorUseCase(
    ):FieldsValidatorUseCase{
        return FieldsValidatorUseCase()
    }

    @Provides
    @Singleton
    fun providePasswordValidatorUseCase(
    ): PasswordValidatorUseCase {
        return PasswordValidatorUseCase()
    }

    @Provides
    @Singleton
    fun providePasswordMatchingUseCase(
    ): PasswordMatchingUseCase {
        return PasswordMatchingUseCase()
    }





//    @Provides
//    @Singleton
//    fun providePlantListUseCase(
//        remotePlantRepository: RemotePlantRepository
//    ): GetPlantListUseCase {
//        return GetPlantListUseCase(
//            remotePlantRepository = remotePlantRepository
//        )
//    }
//
//    @Provides
//    @Singleton
//    fun providePlantDetailUseCase(
//        remotePlantRepository: RemotePlantRepository
//    ): GetPlantDetailUseCase {
//        return GetPlantDetailUseCase(
//            remotePlantRepository = remotePlantRepository
//        )
//    }
//
//    @Provides
//    @Singleton
//    fun provideDeleteFavoritePlantUseCase(
//        localPlantRepository: LocalPlantRepository
//    ): DeleteFavouritePlantUseCase {
//        return DeleteFavouritePlantUseCase(
//            localPlantRepository = localPlantRepository
//        )
//    }
//
//    @Provides
//    @Singleton
//    fun provideGetFavouritePlantForUserUseCase(
//        localPlantRepository: LocalPlantRepository
//    ): GetFavouritePlantsForUserUseCase {
//        return GetFavouritePlantsForUserUseCase(
//            localPlantRepository = localPlantRepository
//        )
//    }
//
//    @Provides
//    @Singleton
//    fun provideInsertFavouritePlantUseCase(
//        localPlantRepository: LocalPlantRepository
//    ): InsertFavouritePlantUseCase {
//        return InsertFavouritePlantUseCase(
//            localPlantRepository = localPlantRepository
//        )
//    }
//
//    @Provides
//    @Singleton
//    fun provideInsertPlantUseCase(
//        localPlantRepository: LocalPlantRepository
//    ): InsertPlantUseCase {
//        return InsertPlantUseCase(
//            localPlantRepository = localPlantRepository
//        )
//    }
//
//    @Provides
//    @Singleton
//    fun provideInsertUserUseCase(
//        localPlantRepository: LocalPlantRepository
//    ): InsertUserUseCase {
//        return InsertUserUseCase(
//            localPlantRepository = localPlantRepository
//        )
//    }
//
//    @Provides
//    @Singleton
//    fun provideResetPasswordUseCase(
//        authRepository: AuthRepository
//    ): ResetPasswordUseCase {
//        return ResetPasswordUseCase(
//            authRepository = authRepository
//        )
//    }
}