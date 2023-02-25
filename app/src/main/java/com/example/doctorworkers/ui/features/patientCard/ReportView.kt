package com.example.doctorworkers.ui.features.patientCard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.doctors.ui.components.AppButton
import com.example.doctorworkers.viewModel.UsersInfoViewModel
import kotlinx.coroutines.Job

@Composable
fun ReportView(patientId: String, hideBottomSheet: () -> Job) {
    var description by remember { mutableStateOf("") }
    val viewModel: UsersInfoViewModel = viewModel()
    val isHideBottomSheet = viewModel.requestIsSuccess.observeAsState()

    if (isHideBottomSheet.value == true) {
        hideBottomSheet()
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = { description = it })
        AppButton(text = "Сохранить отчет") {
            viewModel.writeReport(userId = patientId, description = description)
        }
    }


}