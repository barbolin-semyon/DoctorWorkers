package com.example.doctorworkers.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctors.entities.History
import com.example.doctors.entities.Patient
import com.example.doctors.util.parseListToothesToListId
import com.example.doctorworkers.model.datebase.AuthFirebaseDataSource
import com.example.doctorworkers.model.datebase.UsersInfoFirebaseDataSource
import com.example.doctorworkers.model.entities.Doctor
import com.example.doctorworkers.model.entities.Toothes
import kotlinx.coroutines.launch
import java.util.*

class UsersInfoViewModel : ViewModel() {
    private val db = UsersInfoFirebaseDataSource
    private val auth = AuthFirebaseDataSource

    private val _doctor = MutableLiveData<Doctor>()
    val doctor: LiveData<Doctor>
        get() = _doctor

    private val _patient = MutableLiveData<Patient>()
    val patient: LiveData<Patient>
        get() = _patient

    fun getPatientInformation(patientId: String) = viewModelScope.launch {
        db.getPatientInfo(userId = patientId)
            .addOnSuccessListener { docScnapshot ->
                val info = docScnapshot.toObject(Patient::class.java)
                if (info != null) {
                    _patient.value = info!!
                } else {
                    showMessageError("Ошибка идентификации пользователя")
                }
            }
    }

    fun updateToothes(patientId: String, toothes: List<Toothes>) = viewModelScope.launch {
        val toothesId = parseListToothesToListId(toothes)
        db.updatePatientToothes(patientId, toothesId)
    }

    fun getDoctorInformation() = viewModelScope.launch {
        val userId = auth.getUser()!!.uid
        db.getDoctorById(idDoctor = userId)
            .addOnSuccessListener { docScnapshot ->
                val doctor = docScnapshot.toObject(Doctor::class.java)
                if (doctor != null) {
                    _doctor.value = doctor!!
                } else {
                    showMessageError("Информация по пользователю не найдена")
                }
            }
    }

    fun writeReport(
        userId: String,
        date: Date = Date(),
        description: String = "",
    ) {
        getDoctorInformation()
        _doctor.observeForever {
            setHistory(
                userId,
                History(
                    date = date,
                    description = description,
                    doctorId = it.id,
                    nameDoctor = it.name
                )
            )

        }
    }

    private fun setHistory(userId: String, history: History) = viewModelScope.launch {
        db.setHistory(userId, history)
            .addOnSuccessListener { }
            .addOnFailureListener { showMessageError(it.message.toString()) }
    }
}