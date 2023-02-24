package com.example.doctorworkers.model.datebase

import com.example.doctors.entities.History
import com.example.doctors.entities.PlaceToWrite
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object PlacesFirebaseDataSource {
    private val firestore = FirebaseFirestore.getInstance()
    private val dispatcher = Dispatchers.Default

    fun getQueryPlaces(doctor: String, year: Int, month: Int, day: Int): Query {
        return firestore
            .collection("doctors").document(doctor)
            .collection("places").whereEqualTo("year", year)
            .whereEqualTo("month", month).whereEqualTo("day", day)
    }

    suspend fun createTakenPlace(placeToWrite: PlaceToWrite) = withContext(dispatcher) {
        return@withContext firestore
            .collection("doctors").document(placeToWrite.idDoctor)
            .collection("places").document(placeToWrite.id)
            .set(placeToWrite)
    }

    suspend fun deleteTakenPlace(placeId: String, idDoctor: String) = withContext(dispatcher) {
        return@withContext firestore
            .collection("doctors").document(idDoctor)
            .collection("places").document(placeId)
            .delete()
    }
}