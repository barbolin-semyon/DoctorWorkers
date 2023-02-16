package com.example.doctorworkers.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctorworkers.model.datebase.AuthFirebaseDataSource
import com.example.doctorworkers.model.entities.Doctor
import com.example.doctorworkers.util.AuthorizationType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel : ViewModel() {
    private val db = AuthFirebaseDataSource

    private val _resultRequestAuthDB = MutableLiveData<Result<String>?>()
    val resultRequestAuthDB: LiveData<Result<String>?>
        get() = _resultRequestAuthDB

    private val _typeAuthorization = MutableLiveData(AuthorizationType.LOADING)
    val typeAuthorization: LiveData<AuthorizationType>
        get() = _typeAuthorization
    fun checkAuthorization() = viewModelScope.launch {
        signOut()
        val result =
            withContext(Dispatchers.Default) { db.getUser() != null }
        _typeAuthorization.value =
            if (result) AuthorizationType.AUTHORIZATION else AuthorizationType.NOT_AUTHORIZATION
    }

    fun signInWithEmail(password: String, email: String) = viewModelScope.launch {
        db.signIn(email, password).addOnSuccessListener {
            _typeAuthorization.value = AuthorizationType.AUTHORIZATION
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
            _typeAuthorization.value = AuthorizationType.AUTHORIZATION
        }
    }

    fun signOut() = viewModelScope.launch {
        withContext(Dispatchers.Default) { db.signOut() }
        _typeAuthorization.value = AuthorizationType.NOT_AUTHORIZATION
    }
}
