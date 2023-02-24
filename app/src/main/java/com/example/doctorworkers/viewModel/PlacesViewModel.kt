package com.example.doctorworkers.viewModel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.*
import com.example.doctors.entities.PlaceToWrite
import com.example.doctorworkers.model.datebase.PlacesFirebaseDataSource
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.launch
import java.util.*

class PlacesViewModel() : ViewModel() {
    private lateinit var userId: String

    private val db = PlacesFirebaseDataSource

    private val _places = MutableLiveData<MutableList<PlaceToWrite>>()
    val places: LiveData<MutableList<PlaceToWrite>>
        get() = _places

    private lateinit var snapshotListenerPlaces: ListenerRegistration

    private fun updateListPlaces(
        value: QuerySnapshot?,
        doctor: String,
        year: Int,
        month: Int,
        day: Int
    ) {
        val tempList = mutableListOf<PlaceToWrite>()

        for (i in 0 until 8) {
            tempList.add(
                PlaceToWrite(
                    doctor, "", i,
                    getTimeByNumber(i), year,
                    month, day, false
                )
            )
        }

        val currentList = value?.toObjects(PlaceToWrite::class.java)

        if (currentList != null) {
            for (place in currentList) {
                tempList[place.number] = place
            }
        }

        _places.value = tempList
    }

    private fun getTimeByNumber(number: Int): String {
        return if (number <= 3) {
            "${number + 9}:00-${number + 10}:00"
        } else {
            "${number + 10}:00-${number + 11}:00"
        }
    }

    fun updateDateForPlaces(
        currentDate: MutableState<Calendar>,
        doctorId: String
    ) {
        disableListenerCollectionPlaces()
        enableListenerCollection(currentDate.value, doctorId = doctorId)
    }

    fun enableListenerCollection(currentDate: Calendar, doctorId: String) {
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val date = currentDate.get(Calendar.DATE)

        val query = db.getQueryPlaces(
            doctorId,
            year,
            month,
            date
        )

        snapshotListenerPlaces =
            query.addSnapshotListener { value, error ->
                updateListPlaces(value, doctorId, year, month, date)
            }
    }

    fun disableListenerCollectionPlaces() = snapshotListenerPlaces.remove()

    fun takeOfPlace(placeId: String, doctorId: String) = viewModelScope.launch {
        val task = db.deleteTakenPlace(placeId, doctorId)

        task.addOnCompleteListener {}
        task.addOnFailureListener { showMessageError("Произошла ошибка. попробуйте снова") }
        task.addOnCanceledListener { showMessageError("Произошла ошибка. попробуйте снова") }
    }

    fun takePlace(placeToWrite: PlaceToWrite, idPatient: String) = viewModelScope.launch {
        placeToWrite.isTaken = true
        placeToWrite.idPatient = idPatient

        val task = db.createTakenPlace(placeToWrite)

        task.addOnCompleteListener {}
        task.addOnFailureListener { showMessageError("Произошла ошибка. попробуйте снова") }
        task.addOnCanceledListener { showMessageError("Произошла ошибка. попробуйте снова") }
    }
}