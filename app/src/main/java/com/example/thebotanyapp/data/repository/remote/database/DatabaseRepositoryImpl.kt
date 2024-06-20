package com.example.thebotanyapp.data.repository.remote.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.example.thebotanyapp.domain.model.plant.Plant
import com.example.thebotanyapp.domain.repository.database.DatabaseRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    db: FirebaseDatabase
) : DatabaseRepository {

    private val database = db.getReference("plants")

//    override suspend fun getPlants(): LiveData<List<Plant>> {
//        val liveData = MutableLiveData<List<Plant>>()
//
//        database.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                Log.d("DatabaseRepositoryImpl", "DataSnapshot: $snapshot")
//                val plants = snapshot.children.mapNotNull { children ->
//                    val id = children.child("id").getValue(Long::class.java)?.toInt() ?: 0
//                    val name = children.child("name").getValue(String::class.java) ?: ""
//                    val species = children.child("species").getValue(String::class.java) ?: ""
//                    val family = children.child("family").getValue(String::class.java) ?: ""
//                    val wateringSchedule = children.child("watering_schedule").getValue(String::class.java) ?: ""
//                    val sunlightRequirement = children.child("sunlight_requirement").getValue(String::class.java) ?: ""
//                    val growthHeight = children.child("growth_height").getValue(String::class.java) ?: ""
//                    val bloomingSeason = children.child("blooming_season").getValue(String::class.java) ?: ""
//                    val description = children.child("description").getValue(String::class.java) ?: ""
//                    val image = children.child("image").getValue(String::class.java) ?: ""
//                    val plant = Plant(id, name, species, family, wateringSchedule, sunlightRequirement, growthHeight, bloomingSeason, description, image)
//                    Log.d("DatabaseRepositoryImpl", "Plant: $plant")
//                    plant
//                }
//                liveData.value = plants
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.e("DatabaseRepositoryImpl", "Error fetching data: ${error.message}")
//            }
//        })
//
//        return liveData
//    }

    override suspend fun getPlants(): Flow<List<Plant>> {
        val liveData = MutableLiveData<List<Plant>>()

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("DatabaseRepositoryImpl", "DataSnapshot: $snapshot")
                val plants = snapshot.children.mapNotNull { children ->
                    val id = children.child("id").getValue(Long::class.java)?.toInt() ?: 0
                    val name = children.child("name").getValue(String::class.java) ?: ""
                    val species = children.child("species").getValue(String::class.java) ?: ""
                    val family = children.child("family").getValue(String::class.java) ?: ""
                    val wateringSchedule = children.child("watering_schedule").getValue(String::class.java) ?: ""
                    val sunlightRequirement = children.child("sunlight_requirement").getValue(String::class.java) ?: ""
                    val growthHeight = children.child("growth_height").getValue(String::class.java) ?: ""
                    val bloomingSeason = children.child("blooming_season").getValue(String::class.java) ?: ""
                    val description = children.child("description").getValue(String::class.java) ?: ""
                    val image = children.child("image").getValue(String::class.java) ?: ""
                    val plant = Plant(id, name, species, family, wateringSchedule, sunlightRequirement, growthHeight, bloomingSeason, description, image)
                    Log.d("DatabaseRepositoryImpl", "Plant: $plant")
                    plant
                }
                liveData.value = plants
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("DatabaseError", error.message)
            }
        })

        return liveData.asFlow()
    }

}