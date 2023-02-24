package com.example.doctorworkers.model.datebase

import com.example.doctorworkers.model.entities.Doctor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AuthFirebaseDataSource {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val ioDispatcher = Dispatchers.IO

    fun getUser() = auth.currentUser

    suspend fun signOut() = withContext(ioDispatcher) {
        auth.signOut()
    }

    suspend fun signIn(
        email: String,
        password: String
    ) = withContext(ioDispatcher) {
        return@withContext auth.signInWithEmailAndPassword(email, password)
    }

    suspend fun createUser(
        email: String,
        password: String,
    ) = withContext(ioDispatcher) {
        return@withContext auth.createUserWithEmailAndPassword(email, password)
    }

    suspend fun addDoctorInDatabase(doctor: Doctor) = withContext(ioDispatcher) {
        return@withContext firestore.collection("doctors").document(doctor.id).set(doctor)
    }

    suspend fun getWorkPassword() = withContext(ioDispatcher) {
        return@withContext firestore.collection("information").document("isDoctor").get()
    }
}