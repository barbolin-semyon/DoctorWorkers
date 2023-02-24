package com.example.doctorworkers.ui.features.placeToWrite

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.doctors.entities.PlaceToWrite
import com.example.doctorworkers.ui.navigation.TimeTableScreen
import com.example.doctorworkers.viewModel.AuthViewModel
import com.example.doctorworkers.viewModel.PlacesViewModel
import java.util.*

@Composable
fun PlaceToWriteView(navController: NavController) {
    val viewModel: PlacesViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()
    val doctorId = authViewModel.getDoctorId()
    Column {
        val currentDate = remember { mutableStateOf(Calendar.getInstance()) }

        LaunchedEffect(key1 = Unit, block = {
            viewModel.enableListenerCollection(currentDate.value, doctorId = doctorId)
        })

        LaunchedEffect(key1 = currentDate, block = {
            viewModel.updateDateForPlaces(currentDate = currentDate, doctorId)
        })

        ChangeDate(currentDate)

        val places =
            viewModel.places.observeAsState(listOf()) as MutableState<List<PlaceToWrite>>
        ListPlaces(
            places = places,
            viewModel = viewModel,
            navController = navController
        )
    }
}

@Composable
private fun ListPlaces(
    places: MutableState<List<PlaceToWrite>>,
    navController: NavController,
    viewModel: PlacesViewModel,
) {
    LazyColumn(Modifier.padding(top = 8.dp)) {
        items(places.value) { place ->
            PlaceItem(
                place = place,
                showDetail = { patientId ->
                    navController.currentBackStackEntry?.arguments?.putString(
                        "patientId",
                        patientId
                    )
                    navController.navigate(TimeTableScreen.DetailPlace.route)
                },
                takePlace = { place, doctorId -> viewModel.takePlace(place, doctorId) },
                takeOfPlace = { placeId, doctorId ->
                    viewModel.takeOfPlace(placeId, doctorId)
                }
            )
        }
    }
}
