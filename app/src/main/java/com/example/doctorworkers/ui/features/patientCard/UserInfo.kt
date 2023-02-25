package com.example.doctorworkers.ui.features.patientCard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.doctors.ui.components.AppButton
import com.example.doctors.ui.components.TextWithCaption
import com.example.doctors.util.parseListIdToListToothes
import com.example.doctorworkers.model.entities.getNextToothType
import com.example.doctorworkers.ui.components.BottomActionSheetWithContent
import com.example.doctorworkers.ui.components.mouth.ChangeCurrentTooth
import com.example.doctorworkers.ui.components.mouth.Mouth
import com.example.doctorworkers.ui.theme.Blue200
import com.example.doctorworkers.ui.theme.Blue500
import com.example.doctorworkers.viewModel.UsersInfoViewModel
import kotlinx.coroutines.launch

@Composable
private fun TitleUser(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.h4,
        color = Blue500,
        fontWeight = FontWeight.Bold
    )

    Spacer(
        modifier = Modifier
            .width(200.dp)
            .height(3.dp)
            .background(Blue200)
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserInfoScreen(patientId: String, placeId: String, navController: NavController) {
    BottomActionSheetWithContent(
        sheetContent = { state, scope ->
            ReportView(patientId, navController, placeId) { scope.launch { state.hide() } }
        }
    ) { state, scope ->
        val viewModel: UsersInfoViewModel = viewModel()
        val userInfo by viewModel.patient.observeAsState()
        val indexSelected = remember { mutableStateOf(5) }

        LaunchedEffect(key1 = Unit, block = {
            viewModel.getPatientInformation(patientId)
        })

        userInfo?.let { info ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TitleUser(info.name)

                TextWithCaption(caption = "email:", text = info.email)
                TextWithCaption(caption = "Номер телефона:", text = info.phoneNumber)
                TextWithCaption(caption = "Информация:", text = info.information)

                var toothes by remember { mutableStateOf(parseListIdToListToothes(info.toothes)) }

                Column(
                    verticalArrangement = Arrangement.Bottom
                ) {
                    ChangeCurrentTooth(toothes = toothes, indexSelected = indexSelected) {
                        val temp = toothes.toMutableList()
                        temp[indexSelected.value] = temp[indexSelected.value].getNextToothType()
                        toothes = temp

                        viewModel.updateToothes(patientId, toothes)
                    }
                    Mouth(toothes = toothes, indexSelected = indexSelected)

                    AppButton(modifier = Modifier.padding(top = 128.dp), text = "Вписать отчет") {
                        scope.launch {
                            state.show()
                        }
                    }
                }
            }
        }
    }
}

