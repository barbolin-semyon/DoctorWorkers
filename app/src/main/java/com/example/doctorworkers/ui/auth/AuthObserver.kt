package com.example.doctorworkers.ui.auth

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import com.example.doctorworkers.ui.navigation.Screens
import com.example.doctorworkers.util.AuthorizationType
import com.example.doctorworkers.viewModel.AuthViewModel

@Composable
fun ObserverAuthorizationState(
    viewModel: AuthViewModel,
    navHostController: NavHostController
) {
    val state = viewModel.typeAuthorization.observeAsState()

    val route = when (state.value) {
        AuthorizationType.AUTHORIZATION -> Screens.Main.route
        AuthorizationType.NOT_AUTHORIZATION -> Screens.Auth.route
        else -> Screens.Splash.route
    }

    navHostController.navigate(route) {
        popUpTo(route) {
            inclusive = true
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}