package com.example.doctorworkers.ui.features.placeToWrite

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doctors.entities.PlaceToWrite
import com.example.doctorworkers.R
import com.example.doctorworkers.viewModel.AuthViewModel

@Composable
fun PlaceItem(
    place: PlaceToWrite,
    doctorId: String,
    takePlace: (place: PlaceToWrite, idPatient: String) -> Unit,
    takeOfPlace: (placeId: String, doctorId: String) -> Unit
) {
    Card(
        elevation = 15.dp, modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Row(modifier = Modifier.height(70.dp)) {
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(10.dp),
                color = colorResource(id = getColorIdByIsTaken(place.isTaken)),
            )

            Column {
                Text(text = place.time, fontSize = 24.sp, modifier = Modifier.padding(start = 8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextButton(
                        onClick = { takePlace(place, doctorId) },
                        enabled = place.isTaken.not()
                    ) {
                        Text(text = "Записаться")
                    }

                    if (doctorId == place.idPatient) {
                        TextButton(
                            onClick = { takeOfPlace(place.id, place.idDoctor) },
                        ) {
                            Text(text = "Снять бронь")
                        }
                    }
                }
            }
        }
    }


}

private fun getColorIdByIsTaken(isTaken: Boolean): Int {
    return if (isTaken) {
        R.color.orange_200
    } else {
        R.color.blue_200
    }
}