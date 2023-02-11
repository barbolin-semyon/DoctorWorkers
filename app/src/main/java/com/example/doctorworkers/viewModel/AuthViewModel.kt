package com.example.doctorworkers.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctorworkers.model.datebase.AuthFirebaseDataSource
import com.example.doctorworkers.model.entities.Doctor
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val db = AuthFirebaseDataSource

    private val _resultRequestAuthDB = MutableLiveData<Result<String>?>()
    val resultRequestAuthDB: LiveData<Result<String>?>
        get() = _resultRequestAuthDB

    fun isAuthorization() = db.getUser() != null

    fun signInWithEmail(password: String, email: String) = viewModelScope.launch {
        db.signIn(email, password).addOnSuccessListener {
            _resultRequestAuthDB.value = Result.success("signIn")
        }
    }

    fun registration(
        enteredPasswordWork: String,
        password: String,
        email: String,
        avaragePrice: Int,
        name: String
    ) =
        viewModelScope.launch {
            db.getWorkPassword()
                .addOnSuccessListener {
                    if (enteredPasswordWork == it.get("password")) {
                        createUser(password, email, avaragePrice, name)
                    }
                }
        }

    private fun createUser(
        password: String,
        email: String,
        avaragePrice: Int,
        name: String
    ) = viewModelScope.launch {
        db.createUser(email, password).addOnSuccessListener {
            addUserInDb(avaragePrice, name, it.user!!.uid)
        }
    }

    private fun addUserInDb(
        avaragePrice: Int,
        name: String,
        id: String,
    ) = viewModelScope.launch {
        val doctor = Doctor(id = id, avaragePrice = avaragePrice, name = name)
        db.addDoctorInDatabase(doctor).addOnSuccessListener {
            _resultRequestAuthDB.value = Result.success("registration")
        }
    }

    fun signOut() = viewModelScope.launch { db.signOut() }
}
