package com.example.doctorworkers.ui.features.placeToWrite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doctors.entities.PlaceToWrite
import com.example.doctorworkers.R
import com.example.doctorworkers.ui.theme.Blue200
import com.example.doctorworkers.ui.theme.Gray700
import com.example.doctorworkers.ui.theme.Green
import com.example.doctorworkers.ui.theme.Orange200

@Composable
fun PlaceItem(
    place: PlaceToWrite,
    takePlace: (place: PlaceToWrite, idPatient: String) -> Unit,
    takeOfPlace: (placeId: String, doctorId: String) -> Unit,
    showDetail: (patientId: String) -> Unit
) {
    Card(
        elevation = 15.dp, modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                if (place.idDoctor != place.idPatient && place.isTaken && place.hasReport.not()) {
                    showDetail(place.idPatient)
                }
            }
    ) {

        Row(modifier = Modifier.height(70.dp)) {
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(10.dp),
                color = getColorIdByIsTaken(
                    place.isTaken,
                    place.idDoctor == place.idPatient,
                    place.hasReport
                )
            )

            Column {
                Text(text = place.time, fontSize = 24.sp, modifier = Modifier.padding(start = 8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextButton(
                        onClick = { takePlace(place, place.idDoctor) },
                        enabled = place.isTaken.not()
                    ) {
                        Text(text = "Заморозить бронь")
                    }

                    if (place.idDoctor == place.idPatient) {
                        TextButton(
                            onClick = { takeOfPlace(place.id, place.idDoctor) },
                        ) {
                            Text(text = "Разморозить бронь")
                        }
                    }
                }
            }
        }
    }


}

private fun getColorIdByIsTaken(isTaken: Boolean, isDoctor: Boolean, hasReport: Boolean): Color {
    return if (isTaken) {
        if (isDoctor) {
            Gray700
        } else {
            if (hasReport) {
                Green
            } else {
                Orange200
            }
        }
    } else {
        Blue200
    }
}