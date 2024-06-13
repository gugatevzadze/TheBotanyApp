package com.example.thebotanyapp.data.remote.service.plant

import com.example.thebotanyapp.data.remote.model.plant.PlantDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PlantDetailsApiService {
    @GET("/{id}")
    suspend fun getPlantDetails(@Path("id") id: Int): Response<PlantDto>
}

//interface PlantDetailsApiService {
//    @GET("4044c48f-24d4-4827-a234-6008e824cb07")
//    suspend fun getPlantDetails(): Response<PlantDto>
//}