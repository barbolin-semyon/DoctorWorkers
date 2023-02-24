package com.example.doctorworkers.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctors.entities.Patient
import com.example.doctorworkers.model.datebase.AuthFirebaseDataSource
import com.example.doctorworkers.model.datebase.UsersInfoFirebaseDataSource
import com.example.doctorworkers.model.entities.Doctor
import kotlinx.coroutines.launch

class UsersInfoViewModel : ViewModel() {
    private val db = UsersInfoFirebaseDataSource
    private val auth = AuthFirebaseDataSource

    private val _doctor = MutableLiveData<Doctor>()
    val doctor: LiveData<Doctor>
        get() = _doctor

    private val _patient = MutableLiveData<Patient>()
    val patient: LiveData<Patient>
        get() = _patient
}