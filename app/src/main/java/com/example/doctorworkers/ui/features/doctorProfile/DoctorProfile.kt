package com.example.doctorworkers.ui.features.doctorProfile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.doctors.ui.components.RatingText
import com.example.doctors.ui.components.TextWithCaption
import com.example.doctors.ui.components.Title
import com.example.doctorworkers.viewModel.UsersInfoViewModel
import com.example.doctorworkers.R
import com.example.doctorworkers.ui.theme.*

@Composable
fun DoctorProfile() {
    val viewModel: UsersInfoViewModel = viewModel()
    LaunchedEffect(key1 = Unit, block = {
        viewModel.getDoctorInformation()
    })

    val doctor by viewModel.doctor.observeAsState()
    doctor?.let {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Avatar(it.urlAvatar) {}

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = 16.dp,
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Title(text = it.name, fontSize = 32.sp)

                    TextWithCaption(
                        caption = "price",
                        text = it.avaragePrice.toString(),
                        fontSize = 24.sp
                    )

                    RatingText(rating = it.rating)
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = 16.dp,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_tooth),
                    contentDescription = "",
                    tint = Blue200,
                    modifier = Modifier.size(200.dp).padding(top = 16.dp)
                )
            }
        }
    }
}