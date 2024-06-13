package com.example.thebotanyapp.data.remote.service.plant

import com.example.thebotanyapp.data.remote.model.plant.PlantDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PlantListApiService {
    @GET("/api/v1/plants")
    suspend fun getPlantList(): Response<List<PlantDto>>
}