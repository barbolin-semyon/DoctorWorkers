package com.example.doctorworkers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.doctorworkers.ui.features.auth.ObserverAuthorizationState
import com.example.doctorworkers.ui.navigation.BottomNavigationDoctorWorker
import com.example.doctorworkers.ui.navigation.MainNavHost
import com.example.doctorworkers.ui.theme.MaterialThemeDoctor
import com.example.doctorworkers.viewModel.AuthViewModel
import com.example.doctorworkers.viewModel.messageError

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scaffoldState = rememberScaffoldState()
            val messageError = messageError.observeAsState("")
            LaunchedEffect(key1 = messageError, block = {
                if (messageError.value != "") {
                    scaffoldState.snackbarHostState.showSnackbar(messageError.value)
                }
            })

            val viewModel: AuthViewModel = viewModel()
            viewModel.checkAuthorization()

            val navController = rememberNavController()

            MaterialThemeDoctor() {
                Scaffold(
                    bottomBar = {
                        BottomNavigationDoctorWorker(navController = navController)
                    },
                    scaffoldState = scaffoldState
                ) {

                    MainNavHost(
                        navController = navController,
                        scaffoldState = scaffoldState,
                        authViewModel = viewModel,
                        paddingValues = it
                    )

                    ObserverAuthorizationState(
                        viewModel = viewModel,
                        navHostController = navController
                    )
                }
            }
        }
    }
}