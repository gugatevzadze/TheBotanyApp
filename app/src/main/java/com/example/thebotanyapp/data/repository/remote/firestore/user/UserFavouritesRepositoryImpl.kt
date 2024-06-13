package com.example.thebotanyapp.data.repository.remote.firestore.user

import com.example.thebotanyapp.data.common.Resource
import com.example.thebotanyapp.domain.model.plant.Plant
import com.example.thebotanyapp.domain.repository.firestore.user.UserFavouritesRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserFavouritesRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val user: FirebaseAuth
) : UserFavouritesRepository {

    override suspend fun addFavouritePlant(plant: Plant): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading(true))
            val userId = user.currentUser?.uid
            val userDocRef = db.collection("users").document(userId!!)
            val favouritesCollectionRef = userDocRef.collection("favourites")
            favouritesCollectionRef.document(plant.id.toString()).set(plant)
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(errorMessage = e.toString()))
        }
    }


    override suspend fun removeFavouritePlant(plant: Plant): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading(true))
            val userId = user.currentUser?.uid
            val userDocRef = db.collection("users").document(userId!!)
            val favouritesCollectionRef = userDocRef.collection("favourites")
            favouritesCollectionRef.document(plant.id.toString()).delete()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(errorMessage = e.toString()))
        }
    }

    override suspend fun retrieveFavouritePlants(): Flow<Resource<List<Plant>>> = flow {
        try {
            emit(Resource.Loading(true))
            val userId = user.currentUser?.uid
            val userDocRef = db.collection("users").document(userId!!)
            val favouritesCollectionRef = userDocRef.collection("favourites")
            val snapshot = favouritesCollectionRef.get().await()
            val favouritePlants = snapshot.documents.map { document ->
                Plant(
                    id = document.getLong("id")?.toInt() ?: 0,
                    name = document.getString("name") ?: "",
                    species = document.getString("species") ?: "",
                    family = document.getString("family") ?: "",
                    wateringSchedule = document.getString("wateringSchedule") ?: "",
                    sunlightRequirement = document.getString("sunlightRequirement") ?: "",
                    growthHeight = document.getString("growthHeight") ?: "",
                    bloomingSeason = document.getString("bloomingSeason") ?: "",
                    description = document.getString("description") ?: "",
                    image = document.getString("image") ?: ""
                )
            }
            emit(Resource.Success(favouritePlants))
        } catch (e: Exception) {
            emit(Resource.Error(errorMessage = e.toString()))
        }
    }
}