package com.example.doctorworkers.model.datebase

import com.example.doctors.entities.History
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object UsersInfoFirebaseDataSource {
    private val firestore = FirebaseFirestore.getInstance()
    private val dispatcher = Dispatchers.Default

    suspend fun getDoctorById(idDoctor: String) = withContext(dispatcher) {
        return@withContext firestore.collection("doctors").document(idDoctor).get()
    }

    suspend fun getPatientInfo(userId: String): Task<DocumentSnapshot> = withContext(dispatcher) {
        return@withContext firestore.collection("users").document(userId).get()
    }

    suspend fun setHistory(userId: String, history: History) = withContext(dispatcher) {
        return@withContext firestore
            .collection("users").document(userId)
            .collection("history").document(history.id).set(history)
    }
}