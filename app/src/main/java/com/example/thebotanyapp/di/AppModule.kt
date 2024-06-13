package com.example.thebotanyapp.di

import android.content.Context
import com.example.thebotanyapp.BuildConfig
import com.example.thebotanyapp.data.common.ApiResponseHandler
import com.example.thebotanyapp.data.common.AuthResponseHandler
import com.example.thebotanyapp.data.remote.service.plant.PlantDetailsApiService
import com.example.thebotanyapp.data.remote.service.plant.PlantListApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideResponseHandler(): ApiResponseHandler {
        return ApiResponseHandler()
    }

    @Provides
    @Singleton
    fun provideAuthResponseHandler(): AuthResponseHandler {
        return AuthResponseHandler()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    HttpLoggingInterceptor.Level.BODY
                )
            )
            .build()
    }

    @Singleton
    @Provides
    @Named("ListRetrofit")
    fun provideListRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.LIST_BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            ).client(provideOkHttpClient()).build()
    }

    @Singleton
    @Provides
    @Named("DetailsRetrofit")
    fun provideDetailRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.DETAIL_BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            ).client(provideOkHttpClient()).build()
    }

    ///
//    @Singleton
//    @Provides
//    @Named("DetailsRetrofit")
//    fun provideDetailRetrofitClient(): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(BuildConfig.BASE_URL_MOCKY)
//            .addConverterFactory(
//                MoshiConverterFactory.create(
//                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
//                )
//            ).client(provideOkHttpClient()).build()
//    }
    ///

    @Singleton
    @Provides
    fun providePlantListService(
        @Named("ListRetrofit") listRetrofit: Retrofit
    ): PlantListApiService {
        return listRetrofit.create(PlantListApiService::class.java)
    }

    @Singleton
    @Provides
    fun providePlantDetailsService(
        @Named("DetailsRetrofit") detailRetrofit: Retrofit
    ): PlantDetailsApiService {
        return detailRetrofit.create(PlantDetailsApiService::class.java)
    }
}